package com.api.wslaboratorio.services;

import com.api.wslaboratorio.dto.PaqueteDto;
import com.api.wslaboratorio.entities.PaqueteEntity;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface IPaqueteService {
    PaqueteEntity crearPaquete(PaqueteDto paqueteDto, HttpServletRequest request);

    PaqueteEntity editarPaquete(Long id, PaqueteDto paqueteDto, HttpServletRequest request);

    String eliminarPaquete(Long id);

    PaqueteEntity obtenerPaquetePorId(Long id);

    List<PaqueteEntity> obtenerPaquetes();

}
