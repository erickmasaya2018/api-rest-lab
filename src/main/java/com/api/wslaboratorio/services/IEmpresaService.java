package com.api.wslaboratorio.services;

import com.api.wslaboratorio.dto.EmpresaDto;
import com.api.wslaboratorio.entities.EmpresaEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IEmpresaService {
    EmpresaEntity crearEmpresa(EmpresaDto empresaDto, HttpServletRequest request);

    EmpresaEntity editarEmpresa(Long id, EmpresaDto empresaDto, HttpServletRequest request);

    String eliminarEmpresa(Long id);

    Iterable<EmpresaEntity> obtenerEmpresaPorId(Long id);

    Page<EmpresaEntity> obtenerEmpresas(Pageable pageable);

}
