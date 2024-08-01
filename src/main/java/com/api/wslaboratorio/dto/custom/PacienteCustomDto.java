package com.api.wslaboratorio.dto.custom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PacienteCustomDto {
    private Long pacienteId;
    private String nombreCompleto;
    private List<AnalisisCustomDto> analisis;
}
