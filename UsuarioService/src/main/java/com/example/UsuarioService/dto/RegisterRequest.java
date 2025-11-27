package com.example.UsuarioService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String run;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String genero;
    private String fechaNacimiento;
    private String region;
    private String comuna; // Nombre de comuna como string
    private String direccion;
    private String password;
}
