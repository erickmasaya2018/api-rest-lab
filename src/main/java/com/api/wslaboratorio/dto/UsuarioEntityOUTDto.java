package com.api.wslaboratorio.dto;

import com.api.wslaboratorio.entities.EmpleadoEntity;
import com.api.wslaboratorio.entities.PacienteEntity;
import com.api.wslaboratorio.util.Rol;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 * DTO for {@link com.api.wslaboratorio.entities.UsuarioEntity}
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioEntityOUTDto {
    private String nombreUsuario;
    private String email;
}