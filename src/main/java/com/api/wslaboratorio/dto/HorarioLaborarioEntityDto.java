package com.api.wslaboratorio.dto;

import com.api.wslaboratorio.entities.LaboratorioEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
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

    @NotEmpty(message = "El campo diaSemana no puede estar vacío.")
    @Size(max = 30, message = "El campo nombre tiene como máximo 30 caracteres.")
    private String diaSemana;

    @NotNull(message = "El campo abierto no puede ser nulo.")
    private int abierto;

    @NotNull(message = "El campo nombre no puede ser nulo.")
    @Temporal(TemporalType.TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime horaAbre;

    @NotNull(message = "El campo nombre no puede ser nulo.")
    @Temporal(TemporalType.TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime horaCierra;

    @NotNull(message = "El campo nombre no puede ser nulo.")
    private LaboratorioEntity laboratorioEntity;
}