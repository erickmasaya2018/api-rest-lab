package com.api.wslaboratorio.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link com.api.wslaboratorio.entities.EmpleadoEntity}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpleadoDto implements Serializable {
    private long empleadoId;

    @NotEmpty(message = "El campo dni no puede estar vacío.")
    @Size(max = 20, message = "El campo dni tiene como máximo 30 caracteres.")
    private String dni;

    @NotEmpty(message = "El campo primerNombre no puede estar vacío.")
    @Size(max = 20, message = "El campo primerNombre tiene como máximo 30 caracteres.")
    private String primerNombre;

    @Size(max = 20, message = "El campo segundoNombre tiene como máximo 20 caracteres.")
    private String segundoNombre;

    @Size(max = 20, message = "El campo tercerNombre tiene como máximo 20 caracteres.")
    private String tercerNombre;

    @NotEmpty(message = "El campo primerApellido no puede ser vacío.")
    @Size(max = 20, message = "El campo primerApellido tiene como máximo 30 caracteres.")
    private String primerApellido;

    @Size(max = 20, message = "El campo nombre tiene como máximo 20 caracteres.")
    private String segundoApellido;

    @NotEmpty(message = "El campo dirección no puede ser vacío.")
    @Size(max = 150, message = "El campo direccion tiene como máximo 150 caracteres.")
    private String direccion;

    @NotEmpty(message = "El campo ciudad no puede ser vacío.")
    @Size(max = 20, message = "El campo ciudad tiene como máximo 30 caracteres.")
    private String ciudad;

    @NotEmpty(message = "El campo estadoprovincia no puede ser vacío.")
    @Size(max = 20, message = "El campo estadoprovincia tiene como máximo 30 caracteres.")
    private String estadoprovincia;

    @NotNull
    @Size(max = 30, message = "El campo nombre tiene como máximo 30 caracteres.")
    private Date fechaNacimiento;

    @Size(max = 20, message = "El campo teléfono tiene como máximo 20 caracteres.")
    private String telefono;

    @NotNull(message = "El campo laboratorioId no puede estar null o vació.")
    private Long laboratorioId;

    @NotNull(message = "El campo generoId no puede estar null o vació.")
    private Long generoId;
}