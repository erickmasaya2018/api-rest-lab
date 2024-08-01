package com.api.wslaboratorio.dto;

import com.api.wslaboratorio.entities.EmpresaEntity;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link com.api.wslaboratorio.entities.LaboratorioEntity}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LaboratorioDto implements Serializable {
    private Long laboratorioId;

    @NotEmpty(message = "El campo nombre no puede estar vacío.")
    @Size(max = 30, message = "El campo nombre tiene como máximo 30 caracteres.")
    String nombre;

    @NotEmpty(message = "El campo dirección no puede estar vacío.")
    @Size(max = 30, message = "El campo dirección tiene como máximo 30 caracteres.")
    String direccion;

    @Size(max = 20, message = "El campo teléfono tiene como máximo 30 caracteres.")
    String telefono;

    @Email(message = "Formato incorrecto para el email que ha escrito.")
    @Size(max = 20, message = "El campo email tiene como máximo 30 caracteres.")
    String email;

    @NotNull
    EmpresaEntity empresaEntity;
}