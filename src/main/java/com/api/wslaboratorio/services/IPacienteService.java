package com.api.wslaboratorio.services;

import com.api.wslaboratorio.dto.PacienteDto;
import com.api.wslaboratorio.dto.custom.PacienteCustomDto;
import com.api.wslaboratorio.entities.PacienteEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPacienteService {
    PacienteEntity crearPaciente(PacienteDto pacienteDto, HttpServletRequest request);

    PacienteEntity editarPaciente(Long id, PacienteDto pacienteDto, HttpServletRequest request);

    String eliminarPaciente(Long id);

    Iterable<PacienteEntity> obtenerPacientePorId(Long id);

    Page<PacienteEntity> obtenerPacientes(Pageable pageable);

    PacienteCustomDto obtenerAnalisisPorPacienteId(Long pacienteId);
}
