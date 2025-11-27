package com.example.UsuarioService.controller;

import com.example.UsuarioService.dto.LoginRequest;
import com.example.UsuarioService.dto.LoginResponse;
import com.example.UsuarioService.dto.RefreshTokenRequest;
import com.example.UsuarioService.model.Usuario;
import com.example.UsuarioService.security.CustomUserDetailsService;
import com.example.UsuarioService.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

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

            // Construir respuesta
            LoginResponse response = LoginResponse.builder()
                    .token(token)
                    .refreshToken(refreshToken)
                    .userId(usuario.getIdPersona())
                    .email(usuario.getPersona().getEmail())
                    .nombre(usuario.getPersona().getNombre() + " " + usuario.getPersona().getApellido())
                    .rol(usuario.getRol().getNombreRol())
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

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // En una implementación con lista negra de tokens, aquí se invalidaría el token
        // Por ahora, el logout se maneja en el frontend eliminando el token
        return ResponseEntity.ok("Logout exitoso");
    }
}
