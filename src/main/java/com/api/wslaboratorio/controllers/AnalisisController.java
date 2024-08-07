package com.api.wslaboratorio.controllers;


import com.api.wslaboratorio.dto.AnalisisDto;
import com.api.wslaboratorio.entities.AnalisisEntity;
import com.api.wslaboratorio.services.IAnalisisService;
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

@Tag(name = "analisis", description = "Controlador para gestionar la entidad de analisis.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/${api.path}/analisis")
public class AnalisisController {

    private final IAnalisisService analisisService;

    @PostMapping(produces = "application/json")
    @Operation(summary = "Servicio para crear la entidad analisis.")
    public ResponseEntity<AnalisisEntity> crearEmpleado(@RequestBody @Valid AnalisisDto analisisDto, HttpServletRequest request) {
        var nuevaEmpleado = analisisService.crearAnalisis(analisisDto, request);
        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(nuevaEmpleado.getAnalisisId()).toUri();

        return ResponseEntity.created(ubicacion).body(nuevaEmpleado);
    }

    @PutMapping(produces = "application/json")
    @Operation(summary = "Servicio para editar la entidad analisis.")
    public ResponseEntity<AnalisisEntity> editarEmpleado(
            @Parameter
                    (
                            description = "Campo que contiene el id del empleado a editar.",
                            required = true
                    ) Long id,
            @RequestBody @Valid AnalisisDto analisisDto, HttpServletRequest request) {
        var empleadoActualizado = analisisService.editarAnalisis(id, analisisDto, request);
        if (empleadoActualizado == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(empleadoActualizado);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Servicio para eliminar la entidad analisis.")
    public ResponseEntity<AnalisisEntity> eliminarGenero(
            @Parameter
                    (
                            description = "Campo que contiene el id del empleado a eliminar.",
                            required = true
                    ) Long id
    ) {
        String empleadoEliminado = analisisService.eliminarAnalisis(id);

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
    @Operation(summary = "Servicio para obtener la entidad analisis por el id.")
    public ResponseEntity<AnalisisEntity> obtenerEmpleadoPorId(
            @Parameter
                    (
                            description = "Campo que contiene el id de la empleado a buscar.",
                            required = true
                    ) Long id
    ) {
        var generoEntidad = analisisService.obtenerAnalisisPorId(id);

        if (generoEntidad == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok((AnalisisEntity) generoEntidad);
    }

    @GetMapping(produces = "application/json")
    @Operation(summary = "Servicio para obtener las entidades de analisis.")
    public ResponseEntity<AnalisisEntity> obtenerEmpleados(Pageable pageable) {
        var listaEmpledos = analisisService.obtenerAnalisis(pageable);
        return ResponseEntity.ok((AnalisisEntity) listaEmpledos);
    }

}
