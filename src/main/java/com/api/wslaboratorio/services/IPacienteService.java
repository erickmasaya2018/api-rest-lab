package com.api.wslaboratorio.services;

import com.api.wslaboratorio.dto.PacienteDto;
import com.api.wslaboratorio.dto.custom.PacienteCustomDto;
import com.api.wslaboratorio.entities.PacienteEntity;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface IPacienteService {
    PacienteEntity crearPaciente(PacienteDto pacienteDto, HttpServletRequest request);

    PacienteEntity editarPaciente(Long id, PacienteDto pacienteDto, HttpServletRequest request);

    String eliminarPaciente(Long id);

    PacienteEntity obtenerPacientePorId(Long id);

    List<PacienteEntity> obtenerPacientes();

    PacienteCustomDto obtenerAnalisisPorPacienteId(Long pacienteId);
}
