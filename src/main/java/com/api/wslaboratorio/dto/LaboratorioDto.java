package com.api.wslaboratorio.dto;

import com.api.wslaboratorio.entities.EmpresaEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotNull(message = "El campo nombre no puede ser nulo.")
    @NotBlank(message = "El campo nombre no puede ser vacío.")
    @Size(max = 30, message = "El campo nombre tiene como máximo 30 caracteres.")
    String nombre;

    @NotNull(message = "El campo dirección no puede ser nulo.")
    @NotBlank(message = "El campo dirección no puede ser vacío.")
    @Size(max = 30, message = "El campo dirección tiene como máximo 30 caracteres.")
    String direccion;

    @NotNull(message = "El campo teléfono no puede ser nulo.")
    @NotBlank(message = "El campo teléfono no puede ser vacío.")
    @Size(max = 20, message = "El campo teléfono tiene como máximo 30 caracteres.")
    String telefono;

    @Email
    @Size(max = 20, message = "El campo email tiene como máximo 30 caracteres.")
    String email;

    @NotNull
    EmpresaEntity empresaEntity;
}