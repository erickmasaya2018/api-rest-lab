package com.api.wslaboratorio.services;

import com.api.wslaboratorio.dto.salida.LaboratorioPublicoSalidaDto;

import java.util.List;

public interface ILaboratorioPublicoService {
    List<LaboratorioPublicoSalidaDto> obtenerLaboratorioPublicos();
}
