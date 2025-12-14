package com.example.InventarioService.controller;

import com.example.InventarioService.model.Marca;
import com.example.InventarioService.model.ModeloZapato;
import com.example.InventarioService.repository.ModeloZapatoRepository;
import com.example.InventarioService.service.ModeloZapatoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ModeloZapatoController.class)
class ModeloZapatoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ModeloZapatoService modeloService;

    @MockBean
    private ModeloZapatoRepository modeloRepository;

    private ModeloZapato modelo;
    private Marca marca;

    @BeforeEach
    void setUp() {
        marca = new Marca();
        marca.setIdMarca(1);
        marca.setNombreMarca("Nike");

        modelo = new ModeloZapato();
        modelo.setIdModelo(1);
        modelo.setNombreModelo("Air Max 90");
        modelo.setDescripcion("Zapatillas deportivas");
        modelo.setPrecioUnitario(89990);
        modelo.setEstado("activo");
        modelo.setCategoria("deportivos");
        modelo.setMarca(marca);
    }

    @Test
    void getAllModelos_returnsListOfModelos() throws Exception {
        List<ModeloZapato> modelos = Arrays.asList(modelo);
        when(modeloService.getAllModelos()).thenReturn(modelos);

        mockMvc.perform(get("/api/v1/modelos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idModelo").value(1))
                .andExpect(jsonPath("$[0].nombreModelo").value("Air Max 90"));
    }

    @Test
    void getModeloById_returnsModelo() throws Exception {
        when(modeloService.getModeloById(1)).thenReturn(modelo);

        mockMvc.perform(get("/api/v1/modelos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idModelo").value(1))
                .andExpect(jsonPath("$.nombreModelo").value("Air Max 90"));
    }

    @Test
    void getModelosByMarca_returnsListOfModelos() throws Exception {
        List<ModeloZapato> modelos = Arrays.asList(modelo);
        when(modeloService.getModelosByMarca(1)).thenReturn(modelos);

        mockMvc.perform(get("/api/v1/modelos/marca/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].marca.idMarca").value(1));
    }

    @Test
    void getModelosByEstado_returnsListOfModelos() throws Exception {
        List<ModeloZapato> modelos = Arrays.asList(modelo);
        when(modeloService.getModelosByEstado("activo")).thenReturn(modelos);

        mockMvc.perform(get("/api/v1/modelos/estado/activo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estado").value("activo"));
    }

    @Test
    void createModelo_returnsCreatedModelo() throws Exception {
        when(modeloService.createModelo(any(ModeloZapato.class))).thenReturn(modelo);

        mockMvc.perform(post("/api/v1/modelos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modelo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idModelo").value(1))
                .andExpect(jsonPath("$.nombreModelo").value("Air Max 90"));
    }

    @Test
    void updateModelo_returnsUpdatedModelo() throws Exception {
        when(modeloService.updateModelo(anyInt(), any(ModeloZapato.class))).thenReturn(modelo);

        mockMvc.perform(put("/api/v1/modelos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modelo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idModelo").value(1));
    }

    @Test
    void deleteModelo_returnsNoContent() throws Exception {
        when(modeloService.deleteModelo(1)).thenReturn(modelo);

        mockMvc.perform(delete("/api/v1/modelos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getImagen_returnsImage() throws Exception {
        byte[] imagen = new byte[] { 1, 2, 3 };
        modelo.setImagen(imagen);
        when(modeloRepository.findById(1)).thenReturn(Optional.of(modelo));

        mockMvc.perform(get("/api/v1/modelos/1/imagen"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG))
                .andExpect(content().bytes(imagen));
    }

    @Test
    void getImagen_returnsNotFoundWhenModeloNotExists() throws Exception {
        when(modeloRepository.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/modelos/1/imagen"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getImagen_returnsNotFoundWhenImagenIsNull() throws Exception {
        modelo.setImagen(null);
        when(modeloRepository.findById(1)).thenReturn(Optional.of(modelo));

        mockMvc.perform(get("/api/v1/modelos/1/imagen"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getImagen_returnsNotFoundWhenImagenIsEmpty() throws Exception {
        modelo.setImagen(new byte[0]);
        when(modeloRepository.findById(1)).thenReturn(Optional.of(modelo));

        mockMvc.perform(get("/api/v1/modelos/1/imagen"))
                .andExpect(status().isNotFound());
    }
}
