package com.api.wslaboratorio.services;

import com.api.wslaboratorio.dto.EmpresaDto;
import com.api.wslaboratorio.entities.EmpresaEntity;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface IEmpresaService {
    EmpresaEntity crearEmpresa(EmpresaDto empresaDto, HttpServletRequest request);

    EmpresaEntity editarEmpresa(Long id, EmpresaDto empresaDto, HttpServletRequest request);

    String eliminarEmpresa(Long id);

    EmpresaEntity obtenerEmpresaPorId(Long id);

    List<EmpresaEntity> obtenerEmpresas();

}
