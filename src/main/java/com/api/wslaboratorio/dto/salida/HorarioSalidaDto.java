package com.api.wslaboratorio.dto.salida;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HorarioSalidaDto {

    private String dia;
    private String hora;

}
