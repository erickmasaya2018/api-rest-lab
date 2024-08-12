package com.api.wslaboratorio.dto.salida;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicioPublicoSalidaDto {
    private Long servicioId;
    private String nombre;
    private String descripcion;
    private Double costo;
}
