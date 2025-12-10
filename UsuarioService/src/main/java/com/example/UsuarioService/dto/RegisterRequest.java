package com.example.UsuarioService.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Solicitud de registro de nuevo usuario")
public class RegisterRequest {
    @Schema(description = "RUT del usuario", example = "12345678-9")
    private String run;
    
    @Schema(description = "Nombre del usuario", example = "Juan")
    private String nombre;
    
    @Schema(description = "Apellido del usuario", example = "Pérez")
    private String apellido;
    
    @Schema(description = "Correo electrónico", example = "juan.perez@mail.com")
    private String email;
    
    @Schema(description = "Número de teléfono", example = "+56912345678")
    private String telefono;
    
    @Schema(description = "Género", example = "Masculino")
    private String genero;
    
    @Schema(description = "Fecha de nacimiento", example = "1990-05-15")
    private String fechaNacimiento;
    
    @Schema(description = "Región", example = "Metropolitana")
    private String region;
    
    @Schema(description = "ID de la comuna", example = "1")
    private String comuna;
    
    @Schema(description = "Dirección", example = "Av. Principal 123")
    private String direccion;
    
    @Schema(description = "Contraseña", example = "password123")
    private String password;
}
