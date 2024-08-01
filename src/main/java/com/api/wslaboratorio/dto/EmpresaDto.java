package com.api.wslaboratorio.dto;

import com.api.wslaboratorio.entities.EmpresaEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotNull(message = "El campo nombre no puede ser nulo.")
    @NotBlank(message = "El campo nombre no puede ser vacío.")
    @Size(max = 30, message = "El campo nombre tiene como máximo 30 caracteres.")
    private String nombre;

    @NotNull(message = "El campo dirección no puede ser nulo.")
    @NotBlank(message = "El campo dirección no puede ser vacío.")
    @Size(max = 150, message = "El campo dirección tiene como máximo 150 caracteres.")
    private String direccion;

    @NotNull(message = "El campo ciudad no puede ser nulo.")
    @NotBlank(message = "El campo ciudad no puede ser vacío.")
    @Size(max = 30, message = "El campo ciudad tiene como máximo 30 caracteres.")
    private String ciudad;

    @NotNull(message = "El campo estado provincia no puede ser nulo.")
    @NotBlank(message = "El campo estado provincia no puede ser vacío.")
    @Size(max = 30, message = "El campo estado provincia tiene como máximo 30 caracteres.")
    private String estadoProvincia;

    @NotNull(message = "El campo país provincia no puede ser nulo.")
    @NotBlank(message = "El campo país provincia no puede ser vacío.")
    @Size(max = 30, message = "El campo país provincia tiene como máximo 30 caracteres.")
    private String pais;

    @Size(max = 30, message = "El campo estado provincia tiene como máximo 30 caracteres.")
    private String codigoPostal;

    @Size(max = 30, message = "El campo teléfono provincia tiene como máximo 30 caracteres.")
    private String telefono;

    @Email
    @Size(max = 30, message = "El campo estado provincia tiene como máximo 30 caracteres.")
    private String email;

    @Size(max = 30, message = "El campo estado provincia tiene como máximo 30 caracteres.")
    private String sitioWeb;
}