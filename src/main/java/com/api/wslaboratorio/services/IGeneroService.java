package com.api.wslaboratorio.services;

import com.api.wslaboratorio.dto.GeneroDto;
import com.api.wslaboratorio.entities.GeneroEntity;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface IGeneroService {
    GeneroEntity crearGenero(GeneroDto generoDto, HttpServletRequest request);

    GeneroEntity editarGenero(Long id, GeneroDto generoDto, HttpServletRequest request);

    String eliminarGenero(Long id);

    GeneroEntity obtenerGeneroPorId(Long id);

    List<GeneroEntity> obtenerGeneros();

}
