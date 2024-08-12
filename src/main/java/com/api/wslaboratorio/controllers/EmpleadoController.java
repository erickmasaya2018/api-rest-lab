package com.api.wslaboratorio.controllers;


import com.api.wslaboratorio.dto.EmpleadoDto;
import com.api.wslaboratorio.entities.EmpleadoEntity;
import com.api.wslaboratorio.services.IEmpleadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "empleado", description = "Controlador para gestionar la entidad de empleado.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/empleado")
public class EmpleadoController {

    private final IEmpleadoService empleadoService;

    @PostMapping(produces = "application/json")
    @Operation(summary = "Servicio para crear la entidad empleado.")
    public ResponseEntity<EmpleadoEntity> crearEmpleado(@RequestBody @Valid EmpleadoDto empleadoDto, HttpServletRequest request) {
        var nuevaEmpleado = empleadoService.crearEmpleado(empleadoDto, request);
        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(nuevaEmpleado.getEmpleadoId()).toUri();

        return ResponseEntity.created(ubicacion).body(nuevaEmpleado);
    }

    @PutMapping(value ="/{empleadoId}" ,produces = "application/json")
    @Operation(summary = "Servicio para editar la entidad empleado.")
    public ResponseEntity<EmpleadoEntity> editarEmpleado(
            @Parameter
                    (
                            description = "Campo que contiene el id del empleado a editar.",
                            required = true
                    ) @PathVariable("empleadoId") Long empleadoId,
            @RequestBody @Valid EmpleadoDto empleadoDto, HttpServletRequest request) {
        var empleadoActualizado = empleadoService.editarEmpleado(empleadoId, empleadoDto, request);
        if (empleadoActualizado == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(empleadoActualizado);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Servicio para eliminar la entidad empleado.")
        public ResponseEntity<EmpleadoEntity> eliminarEmpleado(
            @Parameter
                    (
                            description = "Campo que contiene el id del empleado a eliminar.",
                            required = true
                    ) @PathVariable("empleadoId") Long empleadoId
    ) {
        String empleadoEliminado = empleadoService.eliminarEmpleado(empleadoId);

        if (empleadoEliminado == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        if (empleadoEliminado != null) {
            if (empleadoEliminado.equalsIgnoreCase("eliminado")) {

                return ResponseEntity.noContent().build();
            }
        }

        return null;
    }

    @GetMapping(value = "/{empleadoId}", produces = "application/json")
    @Operation(summary = "Servicio para obtener la entidad empleado por el id.")
    public ResponseEntity<EmpleadoEntity> obtenerEmpleadoPorId(
            @Parameter
                    (
                            description = "Campo que contiene el id de la empleado a buscar.",
                            required = true
                    ) @PathVariable("empleadoId") Long empleadoId
    ) {
        var generoEntidad = empleadoService.obtenerEmpleadoPorId(empleadoId);

        if (generoEntidad == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok((EmpleadoEntity) generoEntidad);
    }

    @GetMapping(produces = "application/json")
    @Operation(summary = "Servicio para obtener las entidades de empleado.")
    public ResponseEntity<List<EmpleadoEntity>> obtenerEmpleados() {
        var listaEmpledos = empleadoService.obtenerEmpleados();
        return new ResponseEntity<>(listaEmpledos, HttpStatus.OK);
    }

}
