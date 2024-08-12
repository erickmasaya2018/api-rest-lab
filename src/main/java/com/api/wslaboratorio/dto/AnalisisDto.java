package com.api.wslaboratorio.dto;

import com.api.wslaboratorio.dto.salida.GrupoSalidaDto;
import com.api.wslaboratorio.dto.salida.UnidadSalidaDto;
import com.api.wslaboratorio.entities.AnalisisEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Size(message = "La entidad nombre tiene como maximo 30 caracteres.", max = 100)
    @NotEmpty(message = "La entidad nombre es requerida")
    private String nombre;

    private Double precio;

    private Double minimo;

    private Double maximo;

    @Size(message = "La entidad nombre tiene como maximo 30 caracteres.", max = 2550)
    @NotEmpty(message = "La entidad descripcion es requerida")
    private String descripcion;

    @NotNull(message = "La entidad no debe ser nula")
    private UnidadSalidaDto unidad;

    @NotNull(message = "La entidad no debe ser nula")
    private GrupoSalidaDto grupo;
}