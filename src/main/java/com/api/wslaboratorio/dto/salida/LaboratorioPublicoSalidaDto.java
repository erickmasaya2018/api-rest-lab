package com.api.wslaboratorio.dto.salida;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LaboratorioPublicoSalidaDto {
    private Long laboratorioId;
    private String direccion;
    private String nombre;
    private List<HorarioSalidaDto> horarios;
}
