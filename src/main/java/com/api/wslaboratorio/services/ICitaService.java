package com.api.wslaboratorio.services;

import com.api.wslaboratorio.dto.CitaDto;
import com.api.wslaboratorio.entities.CitaEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICitaService {
    CitaEntity crearCita(CitaDto citaDto, HttpServletRequest request);

    CitaEntity editarCita(Long id, CitaDto citaDto, HttpServletRequest request);

    String eliminarCita(Long id);

    Iterable<CitaEntity> obtenerCitaPorId(Long id);

    Page<CitaEntity> obtenerCitas(Pageable pageable);

}
