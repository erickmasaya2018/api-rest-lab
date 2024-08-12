package com.api.wslaboratorio.services;

import com.api.wslaboratorio.dto.UnidadDto;
import com.api.wslaboratorio.entities.UnidadEntity;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface IUnidadService {
    UnidadEntity crearUnidad(UnidadDto unidadDto, HttpServletRequest request);

    UnidadEntity editarUnidad(Long id, UnidadDto unidadDto, HttpServletRequest request);

    String eliminarUnidad(Long id);

    UnidadEntity obtenerUnidadPorId(Long id);

    List<UnidadEntity> obtenerUnidades();

}
