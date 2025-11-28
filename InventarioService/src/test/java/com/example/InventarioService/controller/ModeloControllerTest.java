package com.example.InventarioService.controller;

import com.example.InventarioService.model.ModeloZapato;
import com.example.InventarioService.service.ModeloService;
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

@WebMvcTest(ModeloController.class)
class ModeloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ModeloService modeloService;

    private ModeloZapato modelo;

    @BeforeEach
    void setUp() {
        modelo = new ModeloZapato();
        modelo.setIdModelo(1);
        modelo.setNombreModelo("Nike Air");
    }

    @Test
    void getAllModelos() throws Exception {
        List<ModeloZapato> modelos = Arrays.asList(modelo);
        when(modeloService.getAllModelos()).thenReturn(modelos);

        mockMvc.perform(get("/api/v1/modelos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idModelo").value(1))
                .andExpect(jsonPath("$[0].nombreModelo").value("Nike Air"));
    }

    @Test
    void getModeloById() throws Exception {
        when(modeloService.getModeloById(1)).thenReturn(modelo);

        mockMvc.perform(get("/api/v1/modelos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idModelo").value(1))
                .andExpect(jsonPath("$.nombreModelo").value("Nike Air"));
    }

    @Test
    void createModelo() throws Exception {
        when(modeloService.createModelo(any(ModeloZapato.class))).thenReturn(modelo);

        mockMvc.perform(post("/api/v1/modelos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\": \"Nike Air\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idModelo").value(1))
                .andExpect(jsonPath("$.nombreModelo").value("Nike Air"));
    }

    @Test
    void updateModelo() throws Exception {
        when(modeloService.updateModelo(anyInt(), any(ModeloZapato.class))).thenReturn(modelo);

        mockMvc.perform(put("/api/v1/modelos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\": \"Nike Air\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idModelo").value(1))
                .andExpect(jsonPath("$.nombreModelo").value("Nike Air"));
    }

    @Test
    void deleteModelo() throws Exception {
        mockMvc.perform(delete("/api/v1/modelos/1"))
                .andExpect(status().isNoContent());
    }
}
