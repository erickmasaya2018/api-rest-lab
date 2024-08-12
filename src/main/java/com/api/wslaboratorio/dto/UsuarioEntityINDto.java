package com.api.wslaboratorio.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link com.api.wslaboratorio.entities.UsuarioEntity}
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioEntityINDto {

    private Long usuarioId;

    @NotEmpty(message = "El campo nombre usuario no puede estar vacío.")
    @Size(max = 30, message = "El campo requiere como máximo 30 caracteres.")
    @Email(
            regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Proporcione un formato correcto de correo electrónico."
    )
    private String email;

    @NotEmpty(message = "El campo contraseña no puede estar vacío.")
    @Size(min = 8, message = "El campo requiere como mínimo 8 caracteres.")
    @Size(max = 20, message = "El campo requiere como máximo 20 caracteres.")
    private String contrasena;

    @NotEmpty(message = "El campo permiso no puede estar vacío.")
    @Size(max = 10, message = "El campo requiere como máximo 10 caracteres.")
    private String permiso;

    private EmpleadoDto empleadoEntity;

    private PacienteDto pacienteEntity;

}