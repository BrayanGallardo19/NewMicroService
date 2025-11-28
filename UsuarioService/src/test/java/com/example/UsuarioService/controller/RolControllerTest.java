package com.example.UsuarioService.controller;

import com.example.UsuarioService.model.Rol;
import com.example.UsuarioService.repository.RolRepository;
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

@WebMvcTest(RolController.class)
@AutoConfigureMockMvc(addFilters = false)
class RolControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RolRepository rolRepository;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    private Rol rol;

    @BeforeEach
    void setUp() {
        rol = new Rol();
        rol.setIdRol(1);
        rol.setNombreRol("ADMIN");
    }

    @Test
    void obtenerTodos() throws Exception {
        List<Rol> roles = Arrays.asList(rol);
        when(rolRepository.findAll()).thenReturn(roles);

        mockMvc.perform(get("/api/v1/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idRol").value(1))
                .andExpect(jsonPath("$[0].nombreRol").value("ADMIN"));
    }

    @Test
    void obtenerPorId() throws Exception {
        when(rolRepository.findById(1)).thenReturn(Optional.of(rol));

        mockMvc.perform(get("/api/v1/roles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idRol").value(1))
                .andExpect(jsonPath("$.nombreRol").value("ADMIN"));
    }

    @Test
    void crear() throws Exception {
        when(rolRepository.save(any(Rol.class))).thenReturn(rol);

        mockMvc.perform(post("/api/v1/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombreRol\": \"ADMIN\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idRol").value(1))
                .andExpect(jsonPath("$.nombreRol").value("ADMIN"));
    }

    @Test
    void actualizar() throws Exception {
        when(rolRepository.existsById(1)).thenReturn(true);
        when(rolRepository.save(any(Rol.class))).thenReturn(rol);

        mockMvc.perform(put("/api/v1/roles/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombreRol\": \"ADMIN\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idRol").value(1))
                .andExpect(jsonPath("$.nombreRol").value("ADMIN"));
    }

    @Test
    void eliminar() throws Exception {
        when(rolRepository.existsById(1)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/roles/1"))
                .andExpect(status().isNoContent());
    }
}
