package com.example.geografiaService.controller;

import com.example.geografiaService.model.Region;
import com.example.geografiaService.service.RegionService;
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

@WebMvcTest(RegionController.class)
class RegionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegionService regionService;

    private Region region;

    @BeforeEach
    void setUp() {
        region = new Region();
        region.setIdRegion(1);
        region.setNombreRegion("Metropolitana");
        region.setCodigoRegion("RM");
    }

    @Test
    void getAllRegiones() throws Exception {
        List<Region> regiones = Arrays.asList(region);
        when(regionService.getAllRegiones()).thenReturn(regiones);

        mockMvc.perform(get("/api/v1/regiones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idRegion").value(1))
                .andExpect(jsonPath("$[0].nombreRegion").value("Metropolitana"));
    }

    @Test
    void getRegionById() throws Exception {
        when(regionService.getRegionById(1)).thenReturn(region);

        mockMvc.perform(get("/api/v1/regiones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idRegion").value(1))
                .andExpect(jsonPath("$.nombreRegion").value("Metropolitana"));
    }

    @Test
    void createRegion() throws Exception {
        when(regionService.createRegion(any(Region.class))).thenReturn(region);

        mockMvc.perform(post("/api/v1/regiones")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombreRegion\": \"Metropolitana\", \"codigoRegion\": \"RM\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idRegion").value(1))
                .andExpect(jsonPath("$.nombreRegion").value("Metropolitana"));
    }

    @Test
    void updateRegion() throws Exception {
        when(regionService.updateRegion(anyInt(), any(Region.class))).thenReturn(region);

        mockMvc.perform(put("/api/v1/regiones/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombreRegion\": \"Metropolitana\", \"codigoRegion\": \"RM\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idRegion").value(1))
                .andExpect(jsonPath("$.nombreRegion").value("Metropolitana"));
    }

    @Test
    void deleteRegion() throws Exception {
        mockMvc.perform(delete("/api/v1/regiones/1"))
                .andExpect(status().isNoContent());
    }
}
