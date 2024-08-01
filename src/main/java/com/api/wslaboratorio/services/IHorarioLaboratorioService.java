package com.api.wslaboratorio.services;

import com.api.wslaboratorio.dto.HorarioLaborarioEntityDto;
import com.api.wslaboratorio.entities.HorarioLaborarioEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IHorarioLaboratorioService {
    HorarioLaborarioEntity crearHorarioLaboratorio(HorarioLaborarioEntityDto horarioLaborarioEntityDto, HttpServletRequest request);

    HorarioLaborarioEntity editarHorarioLaboratorio(Long id, HorarioLaborarioEntityDto horarioLaborarioEntityDto, HttpServletRequest request);

    String eliminarHorarioLaboratorio(Long id);

    Iterable<HorarioLaborarioEntity> obtenerHorarioLaboratorioPorId(Long id);

    Page<HorarioLaborarioEntity> obtenerHorarioLaboratorios(Pageable pageable);

}
