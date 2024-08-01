package com.api.wslaboratorio.dto;

import com.api.wslaboratorio.entities.GrupoEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.api.wslaboratorio.entities.PaqueteEntity}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaqueteDto implements Serializable {
    private Long analisisId;

    @NotNull(message = "El campo nombre no puede ser nulo.")
    @NotBlank(message = "El campo nombre no puede ser vacío.")
    @Size(max = 30, message = "El campo nombre tiene como máximo 30 caracteres.")
    private String nombre;

    @Size(max = 30, message = "El campo nombre tiene como máximo 150 caracteres.")
    private String observacion;

    @NotNull(message = "El campo porcentaje no puede ser nulo.")
    @NotBlank(message = "El campo porcentaje no puede ser vacío.")
    private Double porcentaje;

    private Set<GrupoEntity> grupos;
}