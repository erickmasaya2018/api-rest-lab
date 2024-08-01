package com.api.wslaboratorio.dto;

import com.api.wslaboratorio.entities.AuditoriaEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link com.api.wslaboratorio.entities.GeneroEntity}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeneroDto implements Serializable {
    private Long generoId;

    @NotEmpty(message = "El campo descripción no puede estar vacío.")
    @Size(max = 30, message = "El campo descripción tiene como máximo 30 caracteres.")
    private String descripcion;

    @Size(max = 20, message = "El campo abreviatura tiene como máximo 00 caracteres.")
    private String abreviatura;

    @NotNull(message = "")
    private AuditoriaEntity auditoriaEntity;
}