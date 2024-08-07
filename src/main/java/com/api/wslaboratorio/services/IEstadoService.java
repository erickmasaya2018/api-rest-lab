package com.api.wslaboratorio.services;

import com.api.wslaboratorio.dto.EstadoDto;
import com.api.wslaboratorio.entities.EstadoEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IEstadoService {
    EstadoEntity crearEstado(EstadoDto estadoDto, HttpServletRequest request);

    EstadoEntity editarEstado(Long id, EstadoDto estadoDto, HttpServletRequest request);

    String eliminarEstado(Long id);

    Iterable<EstadoEntity> obtenerEstadoPorId(Long id);

    Page<EstadoEntity> obtenerEstados(Pageable pageable);

}
