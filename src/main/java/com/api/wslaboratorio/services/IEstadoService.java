package com.api.wslaboratorio.services;

import com.api.wslaboratorio.dto.EstadoDto;
import com.api.wslaboratorio.entities.EstadoEntity;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface IEstadoService {
    EstadoEntity crearEstado(EstadoDto estadoDto, HttpServletRequest request);

    EstadoEntity editarEstado(Long id, EstadoDto estadoDto, HttpServletRequest request);

    String eliminarEstado(Long id);

    EstadoEntity obtenerEstadoPorId(Long id);

    List<EstadoEntity> obtenerEstados();

}
