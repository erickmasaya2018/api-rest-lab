package com.api.wslaboratorio.services;

import com.api.wslaboratorio.dto.HorarioLaborarioEntityDto;
import com.api.wslaboratorio.entities.HorarioLaborarioEntity;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface IHorarioLaboratorioService {
    HorarioLaborarioEntity crearHorarioLaboratorio(HorarioLaborarioEntityDto horarioLaborarioEntityDto, HttpServletRequest request);

    HorarioLaborarioEntity editarHorarioLaboratorio(Long id, HorarioLaborarioEntityDto horarioLaborarioEntityDto, HttpServletRequest request);

    String eliminarHorarioLaboratorio(Long id);

    HorarioLaborarioEntity obtenerHorarioLaboratorioPorId(Long id);

    List<HorarioLaborarioEntity> obtenerHorarioLaboratorios();

}
