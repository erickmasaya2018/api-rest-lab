package com.api.wslaboratorio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "El campo nombre usuario es requerido, no puede estar em nulo.")
    @NotBlank(message = "El campo nombre usuario es requerido, no puede estar eb vació.")
    @Size(min = 6, message = "El campo requiere como mínimo 8 caracteres.")
    @Size(max = 30, message = "El campo requiere como máximo 20 caracteres.")
    private String nombreUsuario;

    @NotNull(message = "El campo contraseña es requerido, no puede estar em nulo.")
    @NotBlank(message = "El campo contraseña es requerido, no puede estar eb vació.")
    @Size(min = 8, message = "El campo requiere como mínimo 8 caracteres.")
    @Size(max = 20, message = "El campo requiere como máximo 20 caracteres.")
    private String contrasena;
}
