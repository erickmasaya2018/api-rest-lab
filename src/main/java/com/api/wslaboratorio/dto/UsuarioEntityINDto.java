package com.api.wslaboratorio.dto;

import com.api.wslaboratorio.entities.EmpleadoEntity;
import com.api.wslaboratorio.entities.PacienteEntity;
import com.api.wslaboratorio.util.Rol;
import jakarta.validation.constraints.*;
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
    @Size(min = 6, message = "El campo requiere como mínimo 8 caracteres.")
    @Size(max = 30, message = "El campo requiere como máximo 20 caracteres.")
    private String nombreUsuario;

    @NotEmpty(message = "El campo contraseña no puede estar vacío.")
    @Size(min = 8, message = "El campo requiere como mínimo 8 caracteres.")
    @Size(max = 20, message = "El campo requiere como máximo 20 caracteres.")
    private String contrasena;

    @Email(
            regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Proporcione un formato correcto de correo electrónico."
    )
    private String email;

    private Rol rol;

    private EmpleadoEntity empleadoEntity;

    private PacienteEntity pacienteEntity;

}