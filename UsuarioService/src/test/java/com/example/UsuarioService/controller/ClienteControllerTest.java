package com.example.UsuarioService.controller;

import com.example.UsuarioService.model.Cliente;
import com.example.UsuarioService.repository.ClienteRepository;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
@AutoConfigureMockMvc(addFilters = false)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteRepository clienteRepository;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setIdPersona(1);
    }

    @Test
    void obtenerTodos() throws Exception {
        List<Cliente> clientes = Arrays.asList(cliente);
        when(clienteRepository.findAll()).thenReturn(clientes);

        mockMvc.perform(get("/api/v1/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idPersona").value(1));
    }

    @Test
    void obtenerPorId() throws Exception {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));

        mockMvc.perform(get("/api/v1/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPersona").value(1));
    }

    @Test
    void crear() throws Exception {
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(post("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPersona").value(1));
    }

    @Test
    void actualizar() throws Exception {
        when(clienteRepository.existsById(1)).thenReturn(true);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(put("/api/v1/clientes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPersona").value(1));
    }

    @Test
    void eliminar() throws Exception {
        when(clienteRepository.existsById(1)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/clientes/1"))
                .andExpect(status().isNoContent());
    }
}
