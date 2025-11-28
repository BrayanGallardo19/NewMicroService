package com.example.UsuarioService.controller;

import com.example.UsuarioService.model.Persona;
import com.example.UsuarioService.repository.PersonaRepository;
import com.example.UsuarioService.security.CustomUserDetailsService;
import com.example.UsuarioService.security.JwtAuthenticationFilter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonaController.class)
@AutoConfigureMockMvc(addFilters = false)
class PersonaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonaRepository personaRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    private Persona persona;

    @BeforeEach
    void setUp() {
        persona = new Persona();
        persona.setIdPersona(1);
        persona.setNombre("Juan");
        persona.setApellido("Perez");
        persona.setRut("12345678-9");
        persona.setPassHash("hashedPassword");
    }

    @Test
    void obtenerTodas() throws Exception {
        List<Persona> personas = Arrays.asList(persona);
        when(personaRepository.findAll()).thenReturn(personas);

        mockMvc.perform(get("/api/v1/personas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idPersona").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Juan"));
    }

    @Test
    void obtenerPorId() throws Exception {
        when(personaRepository.findById(1)).thenReturn(Optional.of(persona));

        mockMvc.perform(get("/api/v1/personas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPersona").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test
    void crear() throws Exception {
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(personaRepository.save(any(Persona.class))).thenReturn(persona);

        mockMvc.perform(post("/api/v1/personas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\"nombre\": \"Juan\", \"apellido\": \"Perez\", \"rut\": \"12345678-9\", \"passHash\": \"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPersona").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test
    void actualizar() throws Exception {
        when(personaRepository.existsById(1)).thenReturn(true);
        when(personaRepository.save(any(Persona.class))).thenReturn(persona);

        mockMvc.perform(put("/api/v1/personas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\": \"Juan\", \"apellido\": \"Perez\", \"rut\": \"12345678-9\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPersona").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test
    void eliminar() throws Exception {
        when(personaRepository.existsById(1)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/personas/1"))
                .andExpect(status().isNoContent());
    }
}
