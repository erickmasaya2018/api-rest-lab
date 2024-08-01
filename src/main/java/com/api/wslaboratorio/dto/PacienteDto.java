package com.api.wslaboratorio.dto;

import com.api.wslaboratorio.entities.GeneroEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link com.api.wslaboratorio.entities.PacienteEntity}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PacienteDto implements Serializable {
    Long pacienteId;

    @NotNull(message = "El campo dni no puede ser nulo.")
    @NotBlank(message = "El campo dni no puede ser vacío.")
    @Size(max = 20, message = "El campo dni tiene como máximo 20 caracteres.")
    String dni;

    @NotNull(message = "El campo primerNombre no puede ser nulo.")
    @NotBlank(message = "El campo primerNombre no puede ser vacío.")
    @Size(max = 20, message = "El campo primerNombre tiene como máximo 20 caracteres.")
    String primerNombre;

    @Size(max = 20, message = "El campo segundoNombre tiene como máximo 20 caracteres.")
    String segundoNombre;

    @Size(max = 20, message = "El campo tercerNombre tiene como máximo 20 caracteres.")
    String tercerNombre;

    @NotNull(message = "El campo primerApellido no puede ser nulo.")
    @NotBlank(message = "El campo primerApellido no puede ser vacío.")
    @Size(max = 20, message = "El campo primerApellido tiene como máximo 20 caracteres.")
    String primerApellido;

    @Size(max = 20, message = "El campo segundoApellido tiene como máximo 200 caracteres.")
    String segundoApellido;

    @NotNull(message = "El campo dirección no puede ser nulo.")
    @NotBlank(message = "El campo dirección no puede ser vacío.")
    @Size(max = 150, message = "El campo dirección tiene como máximo 150 caracteres.")
    String direccion;

    @NotNull(message = "El campo ciudad no puede ser nulo.")
    @NotBlank(message = "El campo ciudad no puede ser vacío.")
    @Size(max = 20, message = "El campo dirciudadección tiene como máximo 20 caracteres.")
    String ciudad;

    @Size(max = 20, message = "El campo telefono tiene como máximo 30 caracteres.")
    String telefono;

    @NotNull(message = "El campo dirección no puede ser nulo.")
    @NotBlank(message = "El campo dirección no puede ser vacío.")
    @Size(max = 30, message = "El campo dirección tiene como máximo 150 caracteres.")
    GeneroEntity generoEntity;
}