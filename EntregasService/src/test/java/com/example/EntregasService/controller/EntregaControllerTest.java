package com.example.EntregasService.controller;

import com.example.EntregasService.model.Entrega;
import com.example.EntregasService.service.EntregaService;
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

@WebMvcTest(EntregaController.class)
public class EntregaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EntregaService entregaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllEntregas_returnsList() throws Exception {
        Entrega e1 = new Entrega();
        e1.setIdEntrega(1);
        when(entregaService.getAllEntregas()).thenReturn(List.of(e1));

        mockMvc.perform(get("/api/v1/entregas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idEntrega").value(1));
    }

    @Test
    void getEntregaById_returnsEntrega() throws Exception {
        Entrega e1 = new Entrega();
        e1.setIdEntrega(1);
        when(entregaService.getEntregaById(1)).thenReturn(e1);

        mockMvc.perform(get("/api/v1/entregas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEntrega").value(1));
    }

    @Test
    void createEntrega_returnsCreated() throws Exception {
        Entrega e1 = new Entrega();
        e1.setIdEntrega(1);
        e1.setIdBoleta(100);
        when(entregaService.createEntrega(any(Entrega.class))).thenReturn(e1);

        mockMvc.perform(post("/api/v1/entregas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(e1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idEntrega").value(1));
    }
}
