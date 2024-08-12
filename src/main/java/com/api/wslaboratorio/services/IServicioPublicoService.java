package com.api.wslaboratorio.services;

import com.api.wslaboratorio.dto.salida.ServicioPublicoSalidaDto;

import java.util.List;

public interface IServicioPublicoService {
    List<ServicioPublicoSalidaDto> obtenerServiciosPublicos();
}
