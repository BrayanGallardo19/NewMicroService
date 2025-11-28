package com.example.UsuarioService.controller;

import com.example.UsuarioService.model.Transportista;
import com.example.UsuarioService.repository.TransportistaRepository;
import com.example.UsuarioService.security.CustomUserDetailsService;
import com.example.UsuarioService.security.JwtAuthenticationFilter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransportistaController.class)
@AutoConfigureMockMvc(addFilters = false)
class TransportistaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransportistaRepository transportistaRepository;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    private Transportista transportista;

    @BeforeEach
    void setUp() {
        transportista = new Transportista();
        transportista.setIdPersona(1);
    }

    @Test
    void obtenerTodos() throws Exception {
        List<Transportista> transportistas = Arrays.asList(transportista);
        when(transportistaRepository.findAll()).thenReturn(transportistas);

        mockMvc.perform(get("/api/v1/transportistas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idPersona").value(1));
    }

    @Test
    void obtenerPorId() throws Exception {
        when(transportistaRepository.findById(1)).thenReturn(Optional.of(transportista));

        mockMvc.perform(get("/api/v1/transportistas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPersona").value(1));
    }

    @Test
    void crear() throws Exception {
        when(transportistaRepository.save(any(Transportista.class))).thenReturn(transportista);

        mockMvc.perform(post("/api/v1/transportistas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPersona").value(1));
    }

    @Test
    void actualizar() throws Exception {
        when(transportistaRepository.existsById(1)).thenReturn(true);
        when(transportistaRepository.save(any(Transportista.class))).thenReturn(transportista);

        mockMvc.perform(put("/api/v1/transportistas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPersona").value(1));
    }

    @Test
    void eliminar() throws Exception {
        when(transportistaRepository.existsById(1)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/transportistas/1"))
                .andExpect(status().isNoContent());
    }
}
