package com.api.wslaboratorio.services;

import com.api.wslaboratorio.dto.PaqueteDto;
import com.api.wslaboratorio.entities.PaqueteEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPaqueteService {
    PaqueteEntity crearPaquete(PaqueteDto paqueteDto, HttpServletRequest request);

    PaqueteEntity editarPaquete(Long id, PaqueteDto paqueteDto, HttpServletRequest request);

    String eliminarPaquete(Long id);

    Iterable<PaqueteEntity> obtenerPaquetePorId(Long id);

    Page<PaqueteEntity> obtenerPaquetes(Pageable pageable);

}
