package com.api.wslaboratorio.services;

import com.api.wslaboratorio.dto.AnalisisDto;
import com.api.wslaboratorio.entities.AnalisisEntity;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface IAnalisisService {
    AnalisisEntity crearAnalisis(AnalisisDto analisisDto, HttpServletRequest request);

    AnalisisEntity editarAnalisis(Long id, AnalisisDto analisisDto, HttpServletRequest request);

    String eliminarAnalisis(Long id);

    AnalisisEntity obtenerAnalisisPorId(Long id);

    List<AnalisisEntity> obtenerAnalisis();

}
