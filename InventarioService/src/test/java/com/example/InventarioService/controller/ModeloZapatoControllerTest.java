package com.example.InventarioService.controller;

import com.example.InventarioService.model.ModeloZapato;
import com.example.InventarioService.repository.ModeloZapatoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ModeloZapatoController.class)
class ModeloZapatoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ModeloZapatoRepository modeloRepository;

    @Test
    void getImagen() throws Exception {
        ModeloZapato modelo = new ModeloZapato();
        modelo.setIdModelo(1);
        byte[] imagen = new byte[] { 1, 2, 3 };
        modelo.setImagen(imagen);

        when(modeloRepository.findById(1)).thenReturn(Optional.of(modelo));

        mockMvc.perform(get("/api/v1/modelos/1/imagen"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG))
                .andExpect(content().bytes(imagen));
    }

    @Test
    void getImagenNotFound() throws Exception {
        when(modeloRepository.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/modelos/1/imagen"))
                .andExpect(status().isNotFound());
    }
}
