package com.api.wslaboratorio.dto;

import com.api.wslaboratorio.entities.EmpresaEntity;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link EmpresaEntity}
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpresaDto implements Serializable {
    private Long empresaId;

    @NotEmpty(message = "El campo nombre no puede estar vacío.")
    @Size(max = 30, message = "El campo nombre tiene como máximo 30 caracteres.")
    private String nombre;

    @NotEmpty(message = "El campo dirección no puede estar vacío.")
    @Size(max = 150, message = "El campo dirección tiene como máximo 150 caracteres.")
    private String direccion;

    @NotEmpty(message = "El campo ciudad no puede estar vacío.")
    @Size(max = 30, message = "El campo ciudad tiene como máximo 30 caracteres.")
    private String ciudad;

    @NotEmpty(message = "El campo estadoProvincia no puede estar vacío.")
    @Size(max = 30, message = "El campo estado provincia tiene como máximo 30 caracteres.")
    private String estadoProvincia;

    @NotEmpty(message = "El campo pais no puede estar vacío.")
    @Size(max = 30, message = "El campo país provincia tiene como máximo 30 caracteres.")
    private String pais;

    @Size(max = 30, message = "El campo código postal tiene como máximo 30 caracteres.")
    private String codigoPostal;

    @Size(max = 30, message = "El campo teléfono provincia tiene como máximo 30 caracteres.")
    private String telefono;

    @Email
    @Size(max = 30, message = "El campo estado provincia tiene como máximo 30 caracteres.")
    private String email;

    @Size(max = 30, message = "El campo estado provincia tiene como máximo 30 caracteres.")
    private String sitioWeb;
}