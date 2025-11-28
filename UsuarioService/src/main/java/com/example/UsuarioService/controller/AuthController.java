package com.example.UsuarioService.controller;

import com.example.UsuarioService.dto.LoginRequest;
import com.example.UsuarioService.dto.LoginResponse;
import com.example.UsuarioService.dto.RefreshTokenRequest;
import com.example.UsuarioService.dto.RegisterRequest;
import com.example.UsuarioService.model.Usuario;
import com.example.UsuarioService.repository.PersonaRepository;
import com.example.UsuarioService.repository.RolRepository;
import com.example.UsuarioService.repository.UsuarioRepository;
import com.example.UsuarioService.security.CustomUserDetailsService;
import com.example.UsuarioService.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final com.example.UsuarioService.client.GeografiaClient geografiaClient;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            // Autenticar usuario
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()));

            // Cargar detalles del usuario
            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
            final Usuario usuario = userDetailsService.getUsuarioByEmail(loginRequest.getEmail());

            // Generar tokens
            final String token = jwtUtil.generateToken(userDetails, usuario.getIdPersona());
            final String refreshToken = jwtUtil.generateRefreshToken(userDetails);

            // Obtener región y comuna desde GeografiaService
            String regionNombre = null;
            String comunaNombre = null;

            if (usuario.getPersona().getIdComuna() != null) {
                try {
                    var comuna = geografiaClient.getComunaById(usuario.getPersona().getIdComuna());
                    if (comuna != null) {
                        comunaNombre = comuna.getNombreComuna();
                        if (comuna.getRegion() != null) {
                            regionNombre = comuna.getRegion().getNombreRegion();
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Error al obtener datos de geografía: " + e.getMessage());
                }
            }

            // Construir respuesta con todos los datos del usuario
            LoginResponse response = LoginResponse.builder()
                    .token(token)
                    .refreshToken(refreshToken)
                    .userId(usuario.getIdPersona())
                    .email(usuario.getPersona().getEmail())
                    .nombre(usuario.getPersona().getNombre() + " " + usuario.getPersona().getApellido())
                    .rol(usuario.getRol().getNombreRol())
                    .run(usuario.getPersona().getRut())
                    .telefono(usuario.getPersona().getTelefono())
                    .genero(usuario.getPersona().getGenero())
                    .fechaNacimiento(usuario.getPersona().getFechaNacimiento() != null
                            ? usuario.getPersona().getFechaNacimiento().toString()
                            : null)
                    .region(regionNombre)
                    .comuna(comunaNombre)
                    .direccion(usuario.getPersona().getCalle())
                    .fechaRegistro(usuario.getPersona().getFechaRegistro() != null
                            ? usuario.getPersona().getFechaRegistro().toString()
                            : null)
                    .build();

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error en el servidor: " + e.getMessage());
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        try {
            String refreshToken = request.getRefreshToken();

            // Validar refresh token
            if (!jwtUtil.validateToken(refreshToken)) {
                return ResponseEntity.status(401).body("Refresh token inválido o expirado");
            }

            // Extraer username del refresh token
            String username = jwtUtil.extractUsername(refreshToken);

            // Cargar usuario
            final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            final Usuario usuario = userDetailsService.getUsuarioByEmail(username);

            // Generar nuevo token de acceso
            final String newToken = jwtUtil.generateToken(userDetails, usuario.getIdPersona());

            // Construir respuesta
            LoginResponse response = LoginResponse.builder()
                    .token(newToken)
                    .refreshToken(refreshToken) // Mantener el mismo refresh token
                    .userId(usuario.getIdPersona())
                    .email(usuario.getPersona().getEmail())
                    .nombre(usuario.getPersona().getNombre() + " " + usuario.getPersona().getApellido())
                    .rol(usuario.getRol().getNombreRol())
                    .build();

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al renovar token: " + e.getMessage());
        }
    }

    private final PersonaRepository personaRepository;
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    @org.springframework.transaction.annotation.Transactional
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            // Validar duplicados
            if (personaRepository.findByEmail(request.getEmail()).isPresent()) {
                return ResponseEntity.status(409).body("El email ya está registrado");
            }
            if (personaRepository.findByRut(request.getRun()).isPresent()) {
                return ResponseEntity.status(409).body("El RUT ya está registrado");
            }

            // Crear Persona
            com.example.UsuarioService.model.Persona persona = new com.example.UsuarioService.model.Persona();
            persona.setRut(request.getRun());
            persona.setNombre(request.getNombre());
            persona.setApellido(request.getApellido());
            persona.setEmail(request.getEmail());
            persona.setTelefono(request.getTelefono());
            persona.setCalle(request.getDireccion());
            persona.setGenero(request.getGenero());

            // Parsear fecha de nacimiento
            if (request.getFechaNacimiento() != null && !request.getFechaNacimiento().isEmpty()) {
                try {
                    persona.setFechaNacimiento(java.time.LocalDate.parse(request.getFechaNacimiento()));
                } catch (Exception e) {
                    persona.setFechaNacimiento(null);
                }
            }

            // Parsear comuna (debe venir como ID desde el frontend)
            try {
                persona.setIdComuna(Integer.parseInt(request.getComuna()));
            } catch (NumberFormatException e) {
                persona.setIdComuna(null);
            }
            persona.setUsername(request.getEmail().split("@")[0]);
            persona.setPassHash(passwordEncoder.encode(request.getPassword()));
            persona.setFechaRegistro(java.time.LocalDateTime.now());
            persona.setEstado("activo");

            persona = personaRepository.save(persona);

            // Crear Usuario
            Usuario usuario = new Usuario();
            usuario.setPersona(persona);

            // Asignar Rol Cliente (ID 2)
            com.example.UsuarioService.model.Rol rol = rolRepository.findById(2)
                    .orElseThrow(() -> new RuntimeException("Rol de cliente no encontrado"));
            usuario.setRol(rol);

            usuarioRepository.save(usuario);

            return ResponseEntity.ok("Usuario registrado exitosamente");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error en el registro: " + e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {

        return ResponseEntity.ok("Logout exitoso");
    }
}
