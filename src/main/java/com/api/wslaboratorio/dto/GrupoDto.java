package com.api.wslaboratorio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

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

    @NotNull(message = "El campo dirección no puede ser nulo.")
    @NotBlank(message = "El campo dirección no puede ser vacío.")
    @Size(max = 30, message = "El campo dirección tiene como máximo 150 caracteres.")
    private String nombre;

    @NotNull(message = "El campo dirección no puede ser nulo.")
    @NotBlank(message = "El campo dirección no puede ser vacío.")
    @Size(max = 150, message = "El campo dirección tiene como máximo 150 caracteres.")
    private String observacion;
}