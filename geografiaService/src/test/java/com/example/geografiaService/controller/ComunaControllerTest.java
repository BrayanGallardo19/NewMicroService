package com.example.geografiaService.controller;

import com.example.geografiaService.model.Comuna;
import com.example.geografiaService.model.Region;
import com.example.geografiaService.service.ComunaService;
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

@WebMvcTest(ComunaController.class)
class ComunaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ComunaService comunaService;

    private Comuna comuna;
    private Region region;

    @BeforeEach
    void setUp() {
        region = new Region();
        region.setIdRegion(1);
        region.setNombreRegion("Metropolitana");

        comuna = new Comuna();
        comuna.setIdComuna(1);
        comuna.setNombreComuna("Santiago");
        comuna.setRegion(region);
    }

    @Test
    void getAllComunas() throws Exception {
        List<Comuna> comunas = Arrays.asList(comuna);
        when(comunaService.getAllComunas()).thenReturn(comunas);

        mockMvc.perform(get("/api/v1/comunas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idComuna").value(1))
                .andExpect(jsonPath("$[0].nombreComuna").value("Santiago"));
    }

    @Test
    void getComunaById() throws Exception {
        when(comunaService.getComunaById(1)).thenReturn(comuna);

        mockMvc.perform(get("/api/v1/comunas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idComuna").value(1))
                .andExpect(jsonPath("$.nombreComuna").value("Santiago"));
    }

    @Test
    void getComunasByRegion() throws Exception {
        List<Comuna> comunas = Arrays.asList(comuna);
        when(comunaService.getComunasByRegion(1)).thenReturn(comunas);

        mockMvc.perform(get("/api/v1/comunas/region/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idComuna").value(1))
                .andExpect(jsonPath("$[0].nombreComuna").value("Santiago"));
    }

    @Test
    void createComuna() throws Exception {
        when(comunaService.createComuna(any(Comuna.class))).thenReturn(comuna);

        mockMvc.perform(post("/api/v1/comunas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombreComuna\": \"Santiago\", \"region\": {\"idRegion\": 1}}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idComuna").value(1))
                .andExpect(jsonPath("$.nombreComuna").value("Santiago"));
    }

    @Test
    void updateComuna() throws Exception {
        when(comunaService.updateComuna(anyInt(), any(Comuna.class))).thenReturn(comuna);

        mockMvc.perform(put("/api/v1/comunas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombreComuna\": \"Santiago\", \"region\": {\"idRegion\": 1}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idComuna").value(1))
                .andExpect(jsonPath("$.nombreComuna").value("Santiago"));
    }

    @Test
    void deleteComuna() throws Exception {
        mockMvc.perform(delete("/api/v1/comunas/1"))
                .andExpect(status().isNoContent());
    }
}
