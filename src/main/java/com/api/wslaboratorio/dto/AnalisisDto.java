package com.api.wslaboratorio.dto;

import com.api.wslaboratorio.entities.AnalisisEntity;
import com.api.wslaboratorio.entities.GrupoEntity;
import com.api.wslaboratorio.entities.UnidadEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link AnalisisEntity}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalisisDto implements Serializable {
    private Long analisisId;

    @Size(message = "La entidad nombre tiene como maximo 30 caracteres.", max = 30)
    @NotEmpty(message = "La entidad nombre es requerida")
    private String nombre;

    private Double precio;

    private Double minimo;

    private Double maximo;

    @NotNull(message = "La entidad no debe ser nula")
    private UnidadEntity unidadEntity;

    @NotNull(message = "La entidad no debe ser nula")
    private GrupoEntity grupoEntity;
}