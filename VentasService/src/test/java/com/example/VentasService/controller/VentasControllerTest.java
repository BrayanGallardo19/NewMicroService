package com.example.VentasService.controller;

import com.example.VentasService.model.Boleta;
import com.example.VentasService.service.VentasService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VentasController.class)
public class VentasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VentasService ventasService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllBoletas_returnsList() throws Exception {
        Boleta b1 = new Boleta();
        b1.setIdBoleta(1);
        when(ventasService.getAllBoletas()).thenReturn(List.of(b1));

        mockMvc.perform(get("/api/v1/ventas/boletas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idBoleta").value(1));
    }

    @Test
    void getBoletaById_returnsBoleta() throws Exception {
        Boleta b1 = new Boleta();
        b1.setIdBoleta(1);
        when(ventasService.getBoletaById(1)).thenReturn(b1);

        mockMvc.perform(get("/api/v1/ventas/boletas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idBoleta").value(1));
    }

    @Test
    void createBoleta_returnsCreated() throws Exception {
        Boleta b1 = new Boleta();
        b1.setIdBoleta(1);
        when(ventasService.createBoleta(any(Boleta.class))).thenReturn(b1);

        mockMvc.perform(post("/api/v1/ventas/boletas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(b1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idBoleta").value(1));
    }
}
