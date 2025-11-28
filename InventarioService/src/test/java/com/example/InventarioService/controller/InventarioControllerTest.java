package com.example.InventarioService.controller;

import com.example.InventarioService.model.Inventario;
import com.example.InventarioService.model.ModeloZapato;
import com.example.InventarioService.model.Talla;
import com.example.InventarioService.service.InventarioService;
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

@WebMvcTest(InventarioController.class)
class InventarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventarioService inventarioService;

    private Inventario inventario;

    @BeforeEach
    void setUp() {
        inventario = new Inventario();
        inventario.setIdInventario(1);
        inventario.setStockActual(100);

        ModeloZapato modelo = new ModeloZapato();
        modelo.setIdModelo(1);
        inventario.setModelo(modelo);

        Talla talla = new Talla();
        talla.setIdTalla(1);
        inventario.setTalla(talla);
    }

    @Test
    void getAllInventario() throws Exception {
        List<Inventario> inventarios = Arrays.asList(inventario);
        when(inventarioService.getAllInventario()).thenReturn(inventarios);

        mockMvc.perform(get("/api/v1/inventario"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idInventario").value(1))
                .andExpect(jsonPath("$[0].stockActual").value(100));
    }

    @Test
    void getInventarioById() throws Exception {
        when(inventarioService.getInventarioById(1)).thenReturn(inventario);

        mockMvc.perform(get("/api/v1/inventario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idInventario").value(1))
                .andExpect(jsonPath("$.stockActual").value(100));
    }

    @Test
    void createInventario() throws Exception {
        when(inventarioService.createInventario(any(Inventario.class))).thenReturn(inventario);

        mockMvc.perform(post("/api/v1/inventario")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"stockActual\": 100, \"modelo\": {\"idModelo\": 1}, \"talla\": {\"idTalla\": 1}}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idInventario").value(1))
                .andExpect(jsonPath("$.stockActual").value(100));
    }

    @Test
    void updateInventario() throws Exception {
        when(inventarioService.updateInventario(anyInt(), any(Inventario.class))).thenReturn(inventario);

        mockMvc.perform(put("/api/v1/inventario/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"stockActual\": 100, \"modelo\": {\"idModelo\": 1}, \"talla\": {\"idTalla\": 1}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idInventario").value(1))
                .andExpect(jsonPath("$.stockActual").value(100));
    }

    @Test
    void deleteInventario() throws Exception {
        mockMvc.perform(delete("/api/v1/inventario/1"))
                .andExpect(status().isNoContent());
    }
}
