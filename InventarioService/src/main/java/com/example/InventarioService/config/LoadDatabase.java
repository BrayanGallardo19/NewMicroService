package com.example.InventarioService.config;

import com.example.InventarioService.model.Inventario;
import com.example.InventarioService.model.Marca;
import com.example.InventarioService.model.ModeloZapato;
import com.example.InventarioService.model.Talla;
import com.example.InventarioService.repository.InventarioRepository;
import com.example.InventarioService.repository.MarcaRepository;
import com.example.InventarioService.repository.ModeloZapatoRepository;
import com.example.InventarioService.repository.TallaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@Slf4j
public class LoadDatabase {

        @Bean
        CommandLineRunner initDatabase(
                        MarcaRepository marcaRepository,
                        TallaRepository tallaRepository,
                        ModeloZapatoRepository modeloRepository,
                        InventarioRepository inventarioRepository) {
                return args -> {
                        log.info("Iniciando carga/actualización de datos de inventario...");

                        // 1. Obtener o Crear Marcas
                        Marca nike = marcaRepository.findByNombreMarca("Nike")
                                        .orElseGet(() -> marcaRepository.save(new Marca(null, "Nike",
                                                        "Marca deportiva líder mundial", "activo")));
                        Marca adidas = marcaRepository.findByNombreMarca("Adidas")
                                        .orElseGet(() -> marcaRepository.save(new Marca(null, "Adidas",
                                                        "Marca deportiva alemana", "activo")));
                        Marca puma = marcaRepository.findByNombreMarca("Puma")
                                        .orElseGet(() -> marcaRepository.save(
                                                        new Marca(null, "Puma", "Marca deportiva alemana", "activo")));
                        Marca reebok = marcaRepository.findByNombreMarca("Reebok")
                                        .orElseGet(() -> marcaRepository.save(new Marca(null, "Reebok",
                                                        "Marca deportiva británica", "activo")));

                        log.info("Marcas verificadas/creadas");

                        // 2. Crear Tallas si no existen
                        if (tallaRepository.count() == 0) {
                                for (int i = 35; i <= 45; i++) {
                                        tallaRepository.save(new Talla(null, String.valueOf(i)));
                                }
                                log.info("Tallas creadas: 35 a 45");
                        }
                        List<Talla> tallas = tallaRepository.findAll();

                        // 3. Crear o Actualizar Modelos de Zapatos
                        createOrUpdateModel(modeloRepository, nike, "Air Max 90", "Zapatillas deportivas clásicas",
                                        89990,
                                        "air-max-90.png", "deportivos");

                        createOrUpdateModel(modeloRepository, adidas, "Ultra Boost 22", "Zapatillas running premium",
                                        129990,
                                        "ultra-boost-22.jpg", "deportivos");

                        createOrUpdateModel(modeloRepository, puma, "Suede Classic", "Zapatillas urbanas icónicas",
                                        59990,
                                        "suede-classic.png", "hombre");

                        createOrUpdateModel(modeloRepository, reebok, "Club C 85", "Zapatillas retro casual", 69990,
                                        "club-c-85.jpg", "mujer");

                        createOrUpdateModel(modeloRepository, nike, "Air Jordan 1", "Zapatillas basketball icónicas",
                                        149990,
                                        "air-jordan-1.png", "hombre");

                        // Nuevos productos
                        createOrUpdateModel(modeloRepository, nike, "Botas de Cuero", "Botas resistentes y elegantes",
                                        119990,
                                        "botasdecuero.jpg", "hombre");

                        createOrUpdateModel(modeloRepository, adidas, "Sandalias de Verano", "Comodidad para el verano",
                                        29990,
                                        "sandaliasdeverano.jpg", "mujer");

                        createOrUpdateModel(modeloRepository, puma, "Tacones Elegantes",
                                        "Estilo para ocasiones especiales", 79990,
                                        "taconeselegantes.jpg", "mujer");

                        createOrUpdateModel(modeloRepository, reebok, "Zapatillas Casual", "Para el día a día", 49990,
                                        "zapatillascasual.jpg", "hombre");

                        createOrUpdateModel(modeloRepository, nike, "Zapatillas Deportivas", "Alto rendimiento", 99990,
                                        "zapatillasdeportivas.jpg", "deportivos");

                        createOrUpdateModel(modeloRepository, adidas, "Zapatos de Vestir", "Elegancia formal", 89990,
                                        "zapatosdevestir.jpg", "hombre");

                        createOrUpdateModel(modeloRepository, reebok, "Zapatos Escolares",
                                        "Durabilidad para el colegio", 39990,
                                        "zapatosescolares.jpg", "niños");

                        createOrUpdateModel(modeloRepository, puma, "Oxford Clásicos", "Estilo atemporal", 69990,
                                        "zapatosoxfordclasicos.jpg", "hombre");

                        log.info("Modelos verificados/actualizados");

                        // 4. Crear Inventario (stock por talla) para modelos que no tengan stock
                        List<ModeloZapato> todosModelos = modeloRepository.findAll();
                        for (ModeloZapato modelo : todosModelos) {
                                if (!tallas.isEmpty() && inventarioRepository.findByModelo_IdModeloAndTalla_IdTalla(
                                                modelo.getIdModelo(), tallas.get(0).getIdTalla()).isEmpty()) {
                                        // Si no tiene inventario para la primera talla, asumimos que es nuevo y le
                                        // creamos stock
                                        for (int i = 0; i < 5 && i < tallas.size(); i++) { // Primeras 5 tallas
                                                Talla talla = tallas.get(i);
                                                Inventario inv = new Inventario();
                                                inv.setModelo(modelo);
                                                inv.setTalla(talla);
                                                inv.setStockActual(10 + (int) (Math.random() * 20));
                                                inventarioRepository.save(inv);
                                        }
                                }
                        }

                        log.info("Inventario verificado");
                        log.info("=================================================");
                };
        }

        private void createOrUpdateModel(ModeloZapatoRepository repository, Marca marca, String nombre,
                        String descripcion, int precio, String imagenFile, String categoria) {
                ModeloZapato modelo = repository.findByNombreModelo(nombre)
                                .orElse(new ModeloZapato(null, marca, nombre, descripcion, precio, null, null, "activo",
                                                categoria));

                // Actualizar campos
                modelo.setMarca(marca);
                modelo.setDescripcion(descripcion);
                modelo.setPrecioUnitario(precio);
                modelo.setCategoria(categoria);

                // Cargar imagen solo si es necesario (o forzar actualización)
                byte[] imgData = loadImage(imagenFile);
                if (imgData != null) {
                        modelo.setImagen(imgData);
                }

                // Guardar para generar ID si es nuevo
                modelo = repository.save(modelo);

                // Actualizar URL
                updateImageUrl(repository, modelo);
        }

        private byte[] loadImage(String filename) {
                try {
                        org.springframework.core.io.Resource resource = new org.springframework.core.io.ClassPathResource(
                                        "images/" + filename);
                        return resource.getInputStream().readAllBytes();
                } catch (Exception e) {
                        log.error("Error cargando imagen {}: {}", filename, e.getMessage());
                        return null;
                }
        }

        private void updateImageUrl(ModeloZapatoRepository repository, ModeloZapato modelo) {
                String url = "http://localhost:8082/api/v1/modelos/" + modelo.getIdModelo() + "/imagen";
                modelo.setImagenUrl(url);
                repository.save(modelo);
        }
}
