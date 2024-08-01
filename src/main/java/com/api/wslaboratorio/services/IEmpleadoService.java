package com.api.wslaboratorio.services;

import com.api.wslaboratorio.dto.EmpleadoDto;
import com.api.wslaboratorio.entities.EmpleadoEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IEmpleadoService {
    EmpleadoEntity crearEmpleado(EmpleadoDto empleadoDto, HttpServletRequest request);

    EmpleadoEntity editarEmpleado(Long id, EmpleadoDto empleadoDto, HttpServletRequest request);

    String eliminarEmpleado(Long id);

    Iterable<EmpleadoEntity> obtenerEmpleadoPorId(Long id);

    Page<EmpleadoEntity> obtenerEmpleados(Pageable pageable);

}
