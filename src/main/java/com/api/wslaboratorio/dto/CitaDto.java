package com.api.wslaboratorio.dto;

import com.api.wslaboratorio.entities.CitaEntity;
import com.api.wslaboratorio.entities.EstadoEntity;
import com.api.wslaboratorio.entities.LaboratorioEntity;
import com.api.wslaboratorio.entities.PacienteEntity;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link CitaEntity}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CitaDto implements Serializable {
    private Long citaId;

    @NotNull(message = "La entidad paciente es requerida.")
    private PacienteEntity pacienteEntity;

    @NotNull(message = "La entidad laboratorio es requerida.")
    private LaboratorioEntity laboratorioEntity;

    @NotNull(message = "La entidad estado es requerida.")
    private EstadoEntity estadoEntity;
}