package com.example.UsuarioService.controller;

import com.example.UsuarioService.client.GeografiaClient;
import com.example.UsuarioService.dto.LoginRequest;
import com.example.UsuarioService.dto.LoginResponse;
import com.example.UsuarioService.dto.LogoutRequest;
import com.example.UsuarioService.dto.RefreshTokenRequest;
import com.example.UsuarioService.dto.RegisterRequest;
import com.example.UsuarioService.exception.TokenRefreshException;
import com.example.UsuarioService.model.RefreshToken;
import com.example.UsuarioService.model.Usuario;
import com.example.UsuarioService.repository.PersonaRepository;
import com.example.UsuarioService.repository.RolRepository;
import com.example.UsuarioService.repository.UsuarioRepository;
import com.example.UsuarioService.security.CustomUserDetailsService;
import com.example.UsuarioService.security.JwtUtil;
import com.example.UsuarioService.service.RefreshTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Autenticación", description = "Endpoints para autenticación y gestión de usuarios")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final GeografiaClient geografiaClient;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica un usuario y retorna un token JWT")
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

            // Eliminar refresh tokens existentes antes de crear uno nuevo
            refreshTokenService.deleteByUserId(usuario.getIdPersona());
            final RefreshToken refreshToken = refreshTokenService.createRefreshToken(usuario.getIdPersona());

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
                    .refreshToken(refreshToken.getToken())
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
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(user.getPersona().getEmail());
                    String token = jwtUtil.generateToken(userDetails, user.getIdPersona());

                    // Rotación de token: eliminar el antiguo y crear uno nuevo
                    refreshTokenService.deleteByUserId(user.getIdPersona());
                    RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user.getIdPersona());

                    return ResponseEntity.ok(LoginResponse.builder()
                            .token(token)
                            .refreshToken(newRefreshToken.getToken())
                            .userId(user.getIdPersona())
                            .email(user.getPersona().getEmail())
                            .nombre(user.getPersona().getNombre() + " " + user.getPersona().getApellido())
                            .rol(user.getRol().getNombreRol())
                            .build());
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token no está en la base de datos."));
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
    public ResponseEntity<?> logoutUser(@Valid @RequestBody LogoutRequest logoutRequest) {
        refreshTokenService.deleteByToken(logoutRequest.getRefreshToken());
        return ResponseEntity.ok("Logout exitoso.");
    }
}
