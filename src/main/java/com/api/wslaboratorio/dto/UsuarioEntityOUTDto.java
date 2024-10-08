package com.api.wslaboratorio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link com.api.wslaboratorio.entities.UsuarioEntity}
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioEntityOUTDto {
    private Long usuarioId;
    private String email;
    private String permiso;

}