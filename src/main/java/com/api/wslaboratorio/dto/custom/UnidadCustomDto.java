package com.api.wslaboratorio.dto.custom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnidadCustomDto {
    private Long unidadId;
    private String nombre;
    private String observacion;
}
