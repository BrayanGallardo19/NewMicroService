package com.example.UsuarioService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String token;
    private String refreshToken;
    private String type = "Bearer";
    private Integer userId;
    private String email;
    private String nombre;
    private String rol;
}
