package com.example.InventarioService.controller;

import com.example.InventarioService.model.Talla;
import com.example.InventarioService.service.TallaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TallaController.class)
class TallaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TallaService tallaService;

    private Talla talla;

    @BeforeEach
    void setUp() {
        talla = new Talla();
        talla.setIdTalla(1);
        talla.setNumeroTalla("42");
    }

    @Test
    void getAllTallas() throws Exception {
        List<Talla> tallas = Arrays.asList(talla);
        when(tallaService.getAllTallas()).thenReturn(tallas);

        mockMvc.perform(get("/api/v1/tallas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idTalla").value(1))
                .andExpect(jsonPath("$[0].numeroTalla").value("42"));
    }

    @Test
    void getTallaById() throws Exception {
        when(tallaService.getTallaById(1)).thenReturn(talla);

        mockMvc.perform(get("/api/v1/tallas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idTalla").value(1))
                .andExpect(jsonPath("$.numeroTalla").value("42"));
    }

    @Test
    void createTalla() throws Exception {
        when(tallaService.createTalla(any(Talla.class))).thenReturn(talla);

        mockMvc.perform(post("/api/v1/tallas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"numeroTalla\": 42.0}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idTalla").value(1))
                .andExpect(jsonPath("$.numeroTalla").value("42"));
    }

    @Test
    void updateTalla() throws Exception {
        when(tallaService.updateTalla(anyInt(), any(Talla.class))).thenReturn(talla);

        mockMvc.perform(put("/api/v1/tallas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"numeroTalla\": 42.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idTalla").value(1))
                .andExpect(jsonPath("$.numeroTalla").value("42"));
    }

    @Test
    void deleteTalla() throws Exception {
        mockMvc.perform(delete("/api/v1/tallas/1"))
                .andExpect(status().isNoContent());
    }
}
