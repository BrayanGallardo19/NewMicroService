package com.example.UsuarioService.controller;

import com.example.UsuarioService.model.Persona;
import com.example.UsuarioService.model.Rol;
import com.example.UsuarioService.model.Usuario;
import com.example.UsuarioService.repository.PersonaRepository;
import com.example.UsuarioService.repository.RolRepository;
import com.example.UsuarioService.repository.UsuarioRepository;
import com.example.UsuarioService.security.CustomUserDetailsService;
import com.example.UsuarioService.security.JwtAuthenticationFilter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
@AutoConfigureMockMvc(addFilters = false)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @MockitoBean
    private PersonaRepository personaRepository;

    @MockitoBean
    private RolRepository rolRepository;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    private Usuario usuario;
    private Persona persona;
    private Rol rol;

    @BeforeEach
    void setUp() {
        persona = new Persona();
        persona.setIdPersona(1);
        persona.setNombre("Juan");

        rol = new Rol();
        rol.setIdRol(1);
        rol.setNombreRol("ADMIN");

        usuario = new Usuario();
        usuario.setIdPersona(1); // Usuario ID is same as Persona ID
        usuario.setPersona(persona);
        usuario.setRol(rol);
    }

    @Test
    void obtenerTodos() throws Exception {
        List<Usuario> usuarios = Arrays.asList(usuario);
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idPersona").value(1));
    }

    @Test
    void obtenerPorId() throws Exception {
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/api/v1/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPersona").value(1));
    }

    @Test
    void crear() throws Exception {
        when(personaRepository.findById(1)).thenReturn(Optional.of(persona));
        when(rolRepository.findById(1)).thenReturn(Optional.of(rol));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(post("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"persona\": {\"idPersona\": 1}, \"rol\": {\"idRol\": 1}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPersona").value(1));
    }

    @Test
    void actualizar() throws Exception {
        when(usuarioRepository.existsById(1)).thenReturn(true);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(put("/api/v1/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"persona\": {\"idPersona\": 1}, \"rol\": {\"idRol\": 1}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPersona").value(1));
    }

    @Test
    void eliminar() throws Exception {
        when(usuarioRepository.existsById(1)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/usuarios/1"))
                .andExpect(status().isNoContent());
    }
}
