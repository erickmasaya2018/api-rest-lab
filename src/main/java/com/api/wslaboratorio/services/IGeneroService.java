package com.api.wslaboratorio.services;

import com.api.wslaboratorio.dto.GeneroDto;
import com.api.wslaboratorio.entities.GeneroEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IGeneroService {
    GeneroEntity crearGenero(GeneroDto generoDto, HttpServletRequest request);

    GeneroEntity editarGenero(Long id, GeneroDto generoDto, HttpServletRequest request);

    String eliminarGenero(Long id);

    Iterable<GeneroEntity> obtenerGeneroPorId(Long id);

    Page<GeneroEntity> obtenerGeneros(Pageable pageable);

}
