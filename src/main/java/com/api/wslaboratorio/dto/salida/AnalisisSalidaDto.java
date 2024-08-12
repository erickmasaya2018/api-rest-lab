package com.api.wslaboratorio.dto.salida;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalisisSalidaDto {
    private Long analisisId;
    private String nombre;
    private Double precio;
    private Double minimo;
    private Double maximo;
    private String descripcion;
    private UnidadSalidaDto unidad;
    private GrupoSalidaDto grupo;
}