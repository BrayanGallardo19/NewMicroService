package com.example.geografiaService.config;

import com.example.geografiaService.model.Ciudad;
import com.example.geografiaService.model.Comuna;
import com.example.geografiaService.model.Region;
import com.example.geografiaService.repository.CiudadRepository;
import com.example.geografiaService.repository.ComunaRepository;
import com.example.geografiaService.repository.RegionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(
            RegionRepository regionRepository,
            ComunaRepository comunaRepository,
            CiudadRepository ciudadRepository) {
        return args -> {
            // Verificar si ya hay datos
            if (regionRepository.count() > 0) {
                log.info("Base de datos ya contiene datos. Omitiendo precarga.");
                return;
            }

            log.info("Iniciando precarga de datos geográficos...");

            // Crear Regiones
            Region rm = regionRepository.save(new Region(null, "Región Metropolitana", "RM"));
            Region valparaiso = regionRepository.save(new Region(null, "Valparaíso", "V"));
            Region biobio = regionRepository.save(new Region(null, "Biobío", "VIII"));
            log.info("Regiones creadas: RM, Valparaíso, Biobío");

            // Crear Comunas - Región Metropolitana
            Comuna santiago = comunaRepository.save(new Comuna(null, "Santiago", rm));
            Comuna maipu = comunaRepository.save(new Comuna(null, "Maipú", rm));
            Comuna lasCondes = comunaRepository.save(new Comuna(null, "Las Condes", rm));
            Comuna pudahuel = comunaRepository.save(new Comuna(null, "Pudahuel", rm));
            Comuna puente = comunaRepository.save(new Comuna(null, "Puente Alto", rm));

            // Crear Comunas - Valparaíso
            Comuna valpo = comunaRepository.save(new Comuna(null, "Valparaíso", valparaiso));
            Comuna vina = comunaRepository.save(new Comuna(null, "Viña del Mar", valparaiso));
            Comuna quilpue = comunaRepository.save(new Comuna(null, "Quilpué", valparaiso));

            // Crear Comunas - Biobío
            Comuna conce = comunaRepository.save(new Comuna(null, "Concepción", biobio));
            Comuna talca = comunaRepository.save(new Comuna(null, "Talcahuano", biobio));
            Comuna chillan = comunaRepository.save(new Comuna(null, "Chillán", biobio));

            log.info("Comunas creadas: 11 comunas en total");

            // Crear Ciudades
            ciudadRepository.save(new Ciudad(null, "Santiago Centro", santiago));
            ciudadRepository.save(new Ciudad(null, "Maipú Centro", maipu));
            ciudadRepository.save(new Ciudad(null, "Las Condes Centro", lasCondes));
            ciudadRepository.save(new Ciudad(null, "Valparaíso Puerto", valpo));
            ciudadRepository.save(new Ciudad(null, "Viña del Mar Centro", vina));
            ciudadRepository.save(new Ciudad(null, "Concepción Centro", conce));

            log.info("Ciudades creadas: 6 ciudades principales");
            log.info("Precarga de datos geográficos completada");
        };
    }
}
