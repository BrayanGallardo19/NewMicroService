package com.example.UsuarioService.controller;

import com.example.UsuarioService.client.GeografiaClient;
import com.example.UsuarioService.dto.LoginRequest;
import com.example.UsuarioService.model.Persona;
import com.example.UsuarioService.model.Rol;
import com.example.UsuarioService.model.Usuario;
import com.example.UsuarioService.repository.PersonaRepository;
import com.example.UsuarioService.repository.RolRepository;
import com.example.UsuarioService.repository.UsuarioRepository;
import com.example.UsuarioService.security.CustomUserDetailsService;
import com.example.UsuarioService.security.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false) // Disable security filters for unit testing logic
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private CustomUserDetailsService userDetailsService;

    @MockBean
    private GeografiaClient geografiaClient;

    @MockBean
    private PersonaRepository personaRepository;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private RolRepository rolRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void login_returnsOkAndToken() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password");

        UserDetails userDetails = new User("test@example.com", "password", Collections.emptyList());

        Persona persona = new Persona();
        persona.setEmail("test@example.com");
        persona.setNombre("Test");
        persona.setApellido("User");

        Rol rol = new Rol();
        rol.setNombreRol("CLIENTE");

        Usuario usuario = new Usuario();
        usuario.setIdPersona(1);
        usuario.setPersona(persona);
        usuario.setRol(rol);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);
        when(userDetailsService.loadUserByUsername("test@example.com")).thenReturn(userDetails);
        when(userDetailsService.getUsuarioByEmail("test@example.com")).thenReturn(usuario);
        when(jwtUtil.generateToken(userDetails, 1)).thenReturn("fake-jwt-token");
        when(jwtUtil.generateRefreshToken(userDetails)).thenReturn("fake-refresh-token");

        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("fake-jwt-token"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }
}
