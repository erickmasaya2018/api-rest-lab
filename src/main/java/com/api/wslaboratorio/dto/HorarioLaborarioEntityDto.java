package com.api.wslaboratorio.dto;

import com.api.wslaboratorio.entities.LaboratorioEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * DTO for {@link com.api.wslaboratorio.entities.HorarioLaborarioEntity}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HorarioLaborarioEntityDto implements Serializable {
    private Long horarioLaboratorioId;

    @NotNull(message = "El campo diaSemana no puede ser nulo.")
    @NotBlank(message = "El campo diaSemana no puede ser vacío.")
    @Size(max = 30, message = "El campo nombre tiene como máximo 30 caracteres.")
    private String diaSemana;

    @NotNull(message = "El campo abierto no puede ser nulo.")
    private int abierto;

    @NotNull(message = "El campo nombre no puede ser nulo.")
    private LocalTime horaAbre;

    @NotNull(message = "El campo nombre no puede ser nulo.")
    private LocalTime horaCierra;

    @NotNull(message = "El campo nombre no puede ser nulo.")
    private LaboratorioEntity laboratorioEntity;
}