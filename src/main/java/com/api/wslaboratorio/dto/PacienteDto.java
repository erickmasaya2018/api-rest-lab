package com.api.wslaboratorio.dto;

import com.api.wslaboratorio.entities.GeneroEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @NotEmpty(message = "El campo dni no puede estar vacío.")
    @Size(max = 20, message = "El campo dni tiene como máximo 20 caracteres.")
    String dni;

    @NotEmpty(message = "El campo primer nombre no puede estar vacío.")
    @Size(max = 20, message = "El campo primer nombre tiene como máximo 20 caracteres.")
    String primerNombre;

    @Size(max = 20, message = "El campo segundo nombre tiene como máximo 20 caracteres.")
    String segundoNombre;

    @Size(max = 20, message = "El campo tercer nombre tiene como máximo 20 caracteres.")
    String tercerNombre;

    @NotEmpty(message = "El campo primer apellido no puede estar vacío.")
    @Size(max = 20, message = "El campo primer apellido tiene como máximo 20 caracteres.")
    String primerApellido;

    @Size(max = 20, message = "El campo segundoApellido tiene como máximo 200 caracteres.")
    String segundoApellido;

    @NotEmpty(message = "El campo nombre no puede estar vacío.")
    @Size(max = 150, message = "El campo dirección tiene como máximo 150 caracteres.")
    String direccion;

    @NotEmpty(message = "El campo nombre no puede estar vacío.")
    @Size(max = 20, message = "El campo ciudad tiene como máximo 20 caracteres.")
    String ciudad;

    @Size(max = 20, message = "El campo teléfono tiene como máximo 30 caracteres.")
    String telefono;

    @NotEmpty(message = "El campo nombre no puede estar vacío.")
    @Size(max = 30, message = "El campo dirección tiene como máximo 150 caracteres.")
    GeneroEntity generoEntity;
}