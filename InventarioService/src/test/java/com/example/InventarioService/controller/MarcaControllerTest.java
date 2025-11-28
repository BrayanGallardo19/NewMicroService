package com.example.InventarioService.controller;

import com.example.InventarioService.model.Marca;
import com.example.InventarioService.service.MarcaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MarcaController.class)
public class MarcaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MarcaService marcaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllMarcas_returnsList() throws Exception {
        Marca m1 = new Marca();
        m1.setIdMarca(1);
        m1.setNombreMarca("Nike");

        when(marcaService.getAllMarcas()).thenReturn(List.of(m1));

        mockMvc.perform(get("/api/v1/marcas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idMarca").value(1))
                .andExpect(jsonPath("$[0].nombreMarca").value("Nike"));
    }

    @Test
    void getMarcaById_returnsMarca() throws Exception {
        Marca m1 = new Marca();
        m1.setIdMarca(1);
        m1.setNombreMarca("Nike");

        when(marcaService.getMarcaById(1)).thenReturn(m1);

        mockMvc.perform(get("/api/v1/marcas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idMarca").value(1))
                .andExpect(jsonPath("$.nombreMarca").value("Nike"));
    }

    @Test
    void createMarca_returnsCreated() throws Exception {
        Marca m1 = new Marca();
        m1.setNombreMarca("Nike");

        Marca savedMarca = new Marca();
        savedMarca.setIdMarca(1);
        savedMarca.setNombreMarca("Nike");

        when(marcaService.createMarca(any(Marca.class))).thenReturn(savedMarca);

        mockMvc.perform(post("/api/v1/marcas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(m1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idMarca").value(1))
                .andExpect(jsonPath("$.nombreMarca").value("Nike"));
    }

    @Test
    void updateMarca_returnsUpdated() throws Exception {
        Marca m1 = new Marca();
        m1.setNombreMarca("Nike Updated");

        when(marcaService.updateMarca(eq(1), any(Marca.class))).thenReturn(m1);

        mockMvc.perform(put("/api/v1/marcas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(m1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreMarca").value("Nike Updated"));
    }

    @Test
    void deleteMarca_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/marcas/1"))
                .andExpect(status().isNoContent());
    }
}
