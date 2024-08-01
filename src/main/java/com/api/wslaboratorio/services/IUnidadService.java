package com.api.wslaboratorio.services;

import com.api.wslaboratorio.dto.UnidadDto;
import com.api.wslaboratorio.entities.UnidadEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUnidadService {
    UnidadEntity crearUnidad(UnidadDto unidadDto, HttpServletRequest request);

    UnidadEntity editarUnidad(Long id, UnidadDto unidadDto, HttpServletRequest request);

    String eliminarUnidad(Long id);

    Iterable<UnidadEntity> obtenerUnidadPorId(Long id);

    Page<UnidadEntity> obtenerUnidades(Pageable pageable);

}
