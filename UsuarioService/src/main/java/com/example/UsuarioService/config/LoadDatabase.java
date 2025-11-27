package com.example.UsuarioService.config;

import com.example.UsuarioService.model.Persona;
import com.example.UsuarioService.model.Rol;
import com.example.UsuarioService.model.Usuario;
import com.example.UsuarioService.repository.PersonaRepository;
import com.example.UsuarioService.repository.RolRepository;
import com.example.UsuarioService.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class LoadDatabase {

    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initDatabase(
            RolRepository rolRepository,
            PersonaRepository personaRepository,
            UsuarioRepository usuarioRepository,
            PlatformTransactionManager transactionManager) {
        return args -> {
            new TransactionTemplate(transactionManager).execute(status -> {
                // Verificar si ya hay datos
                if (rolRepository.count() > 0) {
                    log.info("Base de datos ya contiene datos. Omitiendo precarga.");
                    return null;
                }

                log.info("Iniciando precarga de datos...");

                // 1. Crear Roles
                Rol rolAdmin = rolRepository.save(new Rol(null, "ADMIN", "Administrador del sistema"));
                Rol rolCliente = rolRepository.save(new Rol(null, "CLIENTE", "Cliente de la zapatería"));
                Rol rolVendedor = rolRepository.save(new Rol(null, "VENDEDOR", "Vendedor de la zapatería"));
                Rol rolTransportista = rolRepository.save(new Rol(null, "TRANSPORTISTA", "Transportista de entregas"));
                log.info("Roles creados: ADMIN, CLIENTE, VENDEDOR, TRANSPORTISTA");

                // 2. Crear Personas con contraseñas hasheadas
                String passwordHash = passwordEncoder.encode("password123");

                Persona personaAdmin = new Persona();
                personaAdmin.setNombre("Admin");
                personaAdmin.setApellido("Sistema");
                personaAdmin.setRut("11111111-1");
                personaAdmin.setTelefono("912345678");
                personaAdmin.setEmail("admin@zapateria.com");
                personaAdmin.setIdComuna(1);
                personaAdmin.setCalle("Calle Admin");
                personaAdmin.setNumeroPuerta("100");
                personaAdmin.setUsername("admin");
                personaAdmin.setPassHash(passwordHash);
                personaAdmin.setFechaRegistro(LocalDateTime.now());
                personaAdmin.setEstado("activo");
                personaAdmin = personaRepository.save(personaAdmin);

                Persona personaCliente = new Persona();
                personaCliente.setNombre("Juan");
                personaCliente.setApellido("Pérez");
                personaCliente.setRut("22222222-2");
                personaCliente.setTelefono("987654321");
                personaCliente.setEmail("juan@cliente.com");
                personaCliente.setIdComuna(1);
                personaCliente.setCalle("Calle Cliente");
                personaCliente.setNumeroPuerta("200");
                personaCliente.setUsername("juan");
                personaCliente.setPassHash(passwordHash);
                personaCliente.setFechaRegistro(LocalDateTime.now());
                personaCliente.setEstado("activo");
                personaCliente = personaRepository.save(personaCliente);

                Persona personaVendedor = new Persona();
                personaVendedor.setNombre("María");
                personaVendedor.setApellido("González");
                personaVendedor.setRut("33333333-3");
                personaVendedor.setTelefono("956781234");
                personaVendedor.setEmail("maria@vendedor.com");
                personaVendedor.setIdComuna(1);
                personaVendedor.setCalle("Calle Vendedor");
                personaVendedor.setNumeroPuerta("300");
                personaVendedor.setUsername("maria");
                personaVendedor.setPassHash(passwordHash);
                personaVendedor.setFechaRegistro(LocalDateTime.now());
                personaVendedor.setEstado("activo");
                personaVendedor = personaRepository.save(personaVendedor);

                Persona personaTransportista = new Persona();
                personaTransportista.setNombre("Pedro");
                personaTransportista.setApellido("Ramírez");
                personaTransportista.setRut("44444444-4");
                personaTransportista.setTelefono("945678123");
                personaTransportista.setEmail("pedro@transportista.com");
                personaTransportista.setIdComuna(1);
                personaTransportista.setCalle("Calle Transportista");
                personaTransportista.setNumeroPuerta("400");
                personaTransportista.setUsername("pedro");
                personaTransportista.setPassHash(passwordHash);
                personaTransportista.setFechaRegistro(LocalDateTime.now());
                personaTransportista.setEstado("activo");
                personaTransportista = personaRepository.save(personaTransportista);

                log.info("Personas creadas: Admin, Juan, María, Pedro");

                // 3. Crear Usuarios
                Usuario usuarioAdmin = new Usuario();
                usuarioAdmin.setPersona(personaAdmin);
                usuarioAdmin.setRol(rolAdmin);
                usuarioRepository.save(usuarioAdmin);

                Usuario usuarioCliente = new Usuario();
                usuarioCliente.setPersona(personaCliente);
                usuarioCliente.setRol(rolCliente);
                usuarioRepository.save(usuarioCliente);

                Usuario usuarioVendedor = new Usuario();
                usuarioVendedor.setPersona(personaVendedor);
                usuarioVendedor.setRol(rolVendedor);
                usuarioRepository.save(usuarioVendedor);

                Usuario usuarioTransportista = new Usuario();
                usuarioTransportista.setPersona(personaTransportista);
                usuarioTransportista.setRol(rolTransportista);
                usuarioRepository.save(usuarioTransportista);

                log.info("Usuarios creados y vinculados con roles");
                log.info("=================================================");
                log.info("USUARIOS DE PRUEBA (contraseña: password123):");
                log.info("Admin: admin@zapateria.com");
                log.info("Cliente: juan@cliente.com");
                log.info("Vendedor: maria@vendedor.com");
                log.info("Transportista: pedro@transportista.com");
                log.info("=================================================");
                return null;
            });
        };
    }
}
