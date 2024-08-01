package com.api.wslaboratorio.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginUserDto {
    @NotEmpty(message = "El campo nombre usuario no puede estar vacío.")
    @Size(max = 30, message = "El campo requiere como máximo 20 caracteres.")
    private String nombreUsuario;

    @NotEmpty(message = "El campo contraseña no puede estar vacío.")
    @Size(min = 8, message = "El campo requiere como mínimo 8 caracteres.")
    private String contrasena;
}
