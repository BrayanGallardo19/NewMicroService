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
            // Verificar si ya hay datos
            if (marcaRepository.count() > 0) {
                log.info("Base de datos ya contiene datos. Omitiendo precarga.");
                return;
            }

            log.info("Iniciando precarga de datos de inventario...");

            // 1. Crear Marcas
            Marca nike = marcaRepository.save(new Marca(null, "Nike", "Marca deportiva líder mundial", "activo"));
            Marca adidas = marcaRepository.save(new Marca(null, "Adidas", "Marca deportiva alemana", "activo"));
            Marca puma = marcaRepository.save(new Marca(null, "Puma", "Marca deportiva alemana", "activo"));
            Marca reebok = marcaRepository.save(new Marca(null, "Reebok", "Marca deportiva británica", "activo"));
            log.info("Marcas creadas: Nike, Adidas, Puma, Reebok");

            // 2. Crear Tallas
            Talla[] tallas = new Talla[11];
            for (int i = 35; i <= 45; i++) {
                tallas[i - 35] = tallaRepository.save(new Talla(null, String.valueOf(i)));
            }
            log.info("Tallas creadas: 35 a 45");

            // 3. Crear Modelos de Zapatos
            ModeloZapato airMax = modeloRepository.save(new ModeloZapato(
                    null, nike, "Air Max 90", "Zapatillas deportivas clásicas", 89990,
                    "https://static.nike.com/a/images/t_PDP_1280_v1/f_auto,q_auto:eco/zwxes8uud05rkuei1mpt/air-max-90-zapatillas-SLz7VJ.png",
                    "activo"));

            ModeloZapato ultraBoost = modeloRepository.save(new ModeloZapato(
                    null, adidas, "Ultra Boost 22", "Zapatillas running premium", 129990,
                    "https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/fbaf991a78bc4896a3e9ad7800abcec6_9366/Zapatillas_Ultraboost_22_Negro_GZ0127_01_standard.jpg",
                    "activo"));

            ModeloZapato suede = modeloRepository.save(new ModeloZapato(
                    null, puma, "Suede Classic", "Zapatillas urbanas icónicas", 59990,
                    "https://images.puma.com/image/upload/f_auto,q_auto,b_rgb:fafafa,w_2000,h_2000/global/374915/25/sv01/fnd/PNA/fmt/png/Zapatillas-Suede-Classic-XXI",
                    "activo"));

            ModeloZapato club = modeloRepository.save(new ModeloZapato(
                    null, reebok, "Club C 85", "Zapatillas retro casual", 69990,
                    "https://assets.reebok.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/d9f3d4e0e2e54d5f8f3eaad6009a0a3a_9366/Zapatillas_Club_C_85_Vintage_Blanco_FX1378_01_standard.jpg",
                    "activo"));

            ModeloZapato jordan = modeloRepository.save(new ModeloZapato(
                    null, nike, "Air Jordan 1", "Zapatillas basketball icónicas", 149990,
                    "https://static.nike.com/a/images/t_PDP_1280_v1/f_auto,q_auto:eco/b7d9211c-26e7-431a-ac24-b0540fb3c00f/air-jordan-1-mid-zapatillas-SrVlzO.png",
                    "activo"));

            log.info("Modelos creados: 5 modelos de diferentes marcas");

            // 4. Crear Inventario (stock por talla)
            int inventarioCreado = 0;
            ModeloZapato[] modelos = { airMax, ultraBoost, suede, club, jordan };

            for (ModeloZapato modelo : modelos) {
                // Crear inventario para tallas 38-42 (más comunes)
                for (int i = 38; i <= 42; i++) {
                    Talla talla = tallas[i - 35];
                    int stock = 10 + (int) (Math.random() * 20); // Stock aleatorio entre 10 y 30

                    Inventario inv = new Inventario();
                    inv.setModelo(modelo);
                    inv.setTalla(talla);
                    inv.setStockActual(stock);
                    inventarioRepository.save(inv);
                    inventarioCreado++;
                }
            }

            log.info("Inventario creado: {} entradas", inventarioCreado);
            log.info("=================================================");
            log.info("DATOS DE INVENTARIO PRECARGADOS:");
            log.info("- 4 Marcas");
            log.info("- 11 Tallas (35-45)");
            log.info("- 5 Modelos de zapatos");
            log.info("- {} Entradas de inventario", inventarioCreado);
            log.info("=================================================");
        };
    }
}
