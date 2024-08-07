package com.api.wslaboratorio.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link com.api.wslaboratorio.entities.EstadoEntity}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoDto implements Serializable {
    Long estadoId;

    @NotEmpty(message = "El campo descripción no puede estar vacío.")
    @Size(max = 30, message = "El campo nombre tiene como máximo 30 caracteres.")
    String descripcion;
}