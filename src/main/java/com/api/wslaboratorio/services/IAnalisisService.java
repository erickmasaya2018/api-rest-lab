package com.api.wslaboratorio.services;

import com.api.wslaboratorio.dto.AnalisisDto;
import com.api.wslaboratorio.entities.AnalisisEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAnalisisService {
    AnalisisEntity crearAnalisis(AnalisisDto analisisDto, HttpServletRequest request);

    AnalisisEntity editarAnalisis(Long id, AnalisisDto analisisDto, HttpServletRequest request);

    String eliminarAnalisis(Long id);

    Iterable<AnalisisEntity> obtenerAnalisisPorId(Long id);

    Page<AnalisisEntity> obtenerAnalisis(Pageable pageable);

}
