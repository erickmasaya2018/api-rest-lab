package com.api.wslaboratorio.services;

import com.api.wslaboratorio.dto.LaboratorioDto;
import com.api.wslaboratorio.entities.LaboratorioEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ILaboratorioService {
    LaboratorioEntity crearLaboratorio(LaboratorioDto laboratorioDto, HttpServletRequest request);

    LaboratorioEntity editarLaboratorio(Long id, LaboratorioDto laboratorioDto, HttpServletRequest request);

    String eliminarLaboratorio(Long id);

    Iterable<LaboratorioEntity> obtenerLaboratorioPorId(Long id);

    Page<LaboratorioEntity> obtenerLaboratorios(Pageable pageable);

}
