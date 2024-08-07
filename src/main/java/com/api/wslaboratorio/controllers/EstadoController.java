package com.api.wslaboratorio.controllers;


import com.api.wslaboratorio.dto.EstadoDto;
import com.api.wslaboratorio.entities.EstadoEntity;
import com.api.wslaboratorio.services.IEstadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "estado", description = "Controlador para gestionar la entidad de estado.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/${api.path}/estado")
public class EstadoController {

    private final IEstadoService estadoService;

    @PostMapping(produces = "application/json")
    @Operation(summary = "Servicio para crear la entidad estado.")
    public ResponseEntity<EstadoEntity> crearEstado(@RequestBody @Valid EstadoDto estadoDto, HttpServletRequest request) {
        var nuevaEmpleado = estadoService.crearEstado(estadoDto, request);
        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(nuevaEmpleado.getEstadoId()).toUri();

        return ResponseEntity.created(ubicacion).body(nuevaEmpleado);
    }

    @PutMapping(produces = "application/json")
    @Operation(summary = "Servicio para editar la entidad estado.")
    public ResponseEntity<EstadoEntity> editarEstado(
            @Parameter
                    (
                            description = "Campo que contiene el id del estado a editar.",
                            required = true
                    ) Long id,
            @RequestBody @Valid EstadoDto estadoDto, HttpServletRequest request) {
        var empleadoActualizado = estadoService.editarEstado(id, estadoDto, request);
        if (empleadoActualizado == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(empleadoActualizado);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Servicio para eliminar la entidad estado.")
    public ResponseEntity<EstadoEntity> eliminarEstado(
            @Parameter
                    (
                            description = "Campo que contiene el id del estado a eliminar.",
                            required = true
                    ) Long id
    ) {
        String empleadoEliminado = estadoService.eliminarEstado(id);

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

    @GetMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Servicio para obtener la entidad estado por el id.")
    public ResponseEntity<EstadoEntity> obtenerEstadoPorId(
            @Parameter
                    (
                            description = "Campo que contiene el id de la estado a buscar.",
                            required = true
                    ) Long id
    ) {
        var generoEntidad = estadoService.obtenerEstadoPorId(id);

        if (generoEntidad == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok((EstadoEntity) generoEntidad);
    }

    @GetMapping(produces = "application/json")
    @Operation(summary = "Servicio para obtener las entidades de estado.")
    public ResponseEntity<EstadoEntity> obtenerEstados(Pageable pageable) {
        var listaEmpledos = estadoService.obtenerEstados(pageable);
        return ResponseEntity.ok((EstadoEntity) listaEmpledos);
    }

}
