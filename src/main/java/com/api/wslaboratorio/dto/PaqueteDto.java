package com.api.wslaboratorio.dto;

import com.api.wslaboratorio.entities.GrupoEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @NotEmpty(message = "El campo nombre no puede estar vacío.")
    @Size(max = 30, message = "El campo nombre tiene como máximo 30 caracteres.")
    private String nombre;

    @Size(max = 30, message = "El campo nombre tiene como máximo 150 caracteres.")
    private String observacion;

    @NotNull(message = "El campo porcentaje no puede ser nulo.")
    private Double porcentaje;

    @NotNull(message = "El campo grupos no puede ser nulo.")
    private Set<GrupoEntity> grupos;
}