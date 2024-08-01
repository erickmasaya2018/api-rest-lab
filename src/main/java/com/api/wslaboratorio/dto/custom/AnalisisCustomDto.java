package com.api.wslaboratorio.dto.custom;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalisisCustomDto {
    private Long analisisId;
    private Double precio;
    private Double minimo;
    private Double maximo;
    private UnidadCustomDto unidad;
    private GrupoCustomDto grupo;
}
