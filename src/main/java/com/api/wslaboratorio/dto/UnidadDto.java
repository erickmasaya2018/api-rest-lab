package com.api.wslaboratorio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.api.wslaboratorio.entities.UnidadEntity}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnidadDto implements Serializable {
    private Long unidadId;

    @NotNull(message = "El campo nombre no puede ser nulo.")
    @NotBlank(message = "El campo nombre no puede ser vacío.")
    @Size(max = 30, message = "El campo nombre tiene como máximo 150 caracteres.")
    private String nombre;


    @Size(max = 150, message = "El campo observacion tiene como máximo 150 caracteres.")
    private String observacion;
}