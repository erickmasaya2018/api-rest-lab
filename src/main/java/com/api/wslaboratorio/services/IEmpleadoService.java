package com.api.wslaboratorio.services;

import com.api.wslaboratorio.dto.EmpleadoDto;
import com.api.wslaboratorio.entities.EmpleadoEntity;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface IEmpleadoService {
    EmpleadoEntity crearEmpleado(EmpleadoDto empleadoDto, HttpServletRequest request);

    EmpleadoEntity editarEmpleado(Long id, EmpleadoDto empleadoDto, HttpServletRequest request);

    String eliminarEmpleado(Long id);

    EmpleadoEntity obtenerEmpleadoPorId(Long id);

    List<EmpleadoEntity> obtenerEmpleados();

}
