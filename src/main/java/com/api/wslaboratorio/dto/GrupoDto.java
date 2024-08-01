package com.api.wslaboratorio.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.api.wslaboratorio.entities.GrupoEntity}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrupoDto implements Serializable {
    private Long grupoId;

    @NotEmpty(message = "El campo nombre no puede estar vacío.")
    @Size(max = 30, message = "El campo dirección tiene como máximo 150 caracteres.")
    private String nombre;

    @NotEmpty(message = "El campo observación no puede estar vacío.")
    @Size(max = 150, message = "El campo dirección tiene como máximo 150 caracteres.")
    private String observacion;
}