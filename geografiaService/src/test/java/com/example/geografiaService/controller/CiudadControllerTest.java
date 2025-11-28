package com.example.geografiaService.controller;

import com.example.geografiaService.model.Ciudad;
import com.example.geografiaService.model.Comuna;
import com.example.geografiaService.service.CiudadService;
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

@WebMvcTest(CiudadController.class)
class CiudadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CiudadService ciudadService;

    private Ciudad ciudad;
    private Comuna comuna;

    @BeforeEach
    void setUp() {
        comuna = new Comuna();
        comuna.setIdComuna(1);
        comuna.setNombreComuna("Santiago");

        ciudad = new Ciudad();
        ciudad.setIdCiudad(1);
        ciudad.setNombreCiudad("Santiago Centro");
        ciudad.setComuna(comuna);
    }

    @Test
    void getAllCiudades() throws Exception {
        List<Ciudad> ciudades = Arrays.asList(ciudad);
        when(ciudadService.getAllCiudades()).thenReturn(ciudades);

        mockMvc.perform(get("/api/v1/ciudades"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idCiudad").value(1))
                .andExpect(jsonPath("$[0].nombreCiudad").value("Santiago Centro"));
    }

    @Test
    void getCiudadById() throws Exception {
        when(ciudadService.getCiudadById(1)).thenReturn(ciudad);

        mockMvc.perform(get("/api/v1/ciudades/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCiudad").value(1))
                .andExpect(jsonPath("$.nombreCiudad").value("Santiago Centro"));
    }

    @Test
    void getCiudadesByComuna() throws Exception {
        List<Ciudad> ciudades = Arrays.asList(ciudad);
        when(ciudadService.getCiudadesByComuna(1)).thenReturn(ciudades);

        mockMvc.perform(get("/api/v1/ciudades/comuna/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idCiudad").value(1))
                .andExpect(jsonPath("$[0].nombreCiudad").value("Santiago Centro"));
    }

    @Test
    void createCiudad() throws Exception {
        when(ciudadService.createCiudad(any(Ciudad.class))).thenReturn(ciudad);

        mockMvc.perform(post("/api/v1/ciudades")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombreCiudad\": \"Santiago Centro\", \"comuna\": {\"idComuna\": 1}}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idCiudad").value(1))
                .andExpect(jsonPath("$.nombreCiudad").value("Santiago Centro"));
    }

    @Test
    void updateCiudad() throws Exception {
        when(ciudadService.updateCiudad(anyInt(), any(Ciudad.class))).thenReturn(ciudad);

        mockMvc.perform(put("/api/v1/ciudades/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombreCiudad\": \"Santiago Centro\", \"comuna\": {\"idComuna\": 1}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCiudad").value(1))
                .andExpect(jsonPath("$.nombreCiudad").value("Santiago Centro"));
    }

    @Test
    void deleteCiudad() throws Exception {
        mockMvc.perform(delete("/api/v1/ciudades/1"))
                .andExpect(status().isNoContent());
    }
}
