package com.api.wslaboratorio.services;

import com.api.wslaboratorio.dto.LaboratorioDto;
import com.api.wslaboratorio.entities.LaboratorioEntity;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface ILaboratorioService {
    LaboratorioEntity crearLaboratorio(LaboratorioDto laboratorioDto, HttpServletRequest request);

    LaboratorioEntity editarLaboratorio(Long id, LaboratorioDto laboratorioDto, HttpServletRequest request);

    String eliminarLaboratorio(Long id);

    LaboratorioEntity obtenerLaboratorioPorId(Long id);

    List<LaboratorioEntity> obtenerLaboratorios();

}
