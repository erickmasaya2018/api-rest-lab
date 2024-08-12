package com.api.wslaboratorio.controllers;


import com.api.wslaboratorio.dto.UnidadDto;
import com.api.wslaboratorio.entities.UnidadEntity;
import com.api.wslaboratorio.services.IUnidadService;
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

@Tag(name = "unidad", description = "Controlador para gestionar la entidad de unidad.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/unidad")
public class UnidadController {

    private final IUnidadService unidadService;

    @PostMapping(produces = "application/json")
    @Operation(summary = "Servicio para crear la entidad unidad.")
    public ResponseEntity<UnidadEntity> crearUnidad(@RequestBody @Valid UnidadDto unidadDto, HttpServletRequest request) {
        var unidadNueva = unidadService.crearUnidad(unidadDto, request);
        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(unidadNueva.getUnidadId()).toUri();

        return ResponseEntity.created(ubicacion).body(unidadNueva);
    }

    @PutMapping(value = "/{unidadId}", produces = "application/json")
    @Operation(summary = "Servicio para editar la entidad unidad.")
    public ResponseEntity<UnidadEntity> editarUnidad(
            @Parameter
                    (
                            description = "Campo que contiene el id del empresa a editar.",
                            required = true
                    ) @PathVariable("unidadId") Long unidadId,
            @RequestBody @Valid UnidadDto unidadDto, HttpServletRequest request) {
        var unidadActualizada = unidadService.editarUnidad(unidadId, unidadDto, request);
        if (unidadActualizada == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(unidadActualizada);
    }


    @DeleteMapping(value = "/{unidadId}", produces = "application/json")
    @Operation(summary = "Servicio para eliminar la entidad unidad.")
    public ResponseEntity<UnidadEntity> eliminarUnidad(
            @Parameter
                    (
                            description = "Campo que contiene el id del empresa a eliminar.",
                            required = true
                    ) @PathVariable("unidadId") Long unidadId
    ) {
        String unidadEliminada = unidadService.eliminarUnidad(unidadId);

        if (unidadEliminada == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        if (unidadEliminada != null) {
            if (unidadEliminada.equalsIgnoreCase("eliminado")) {

                return ResponseEntity.noContent().build();
            }
        }

        return null;
    }

    @GetMapping(value = "/{unidadId}", produces = "application/json")
    @Operation(summary = "Servicio para obtener la entidad unidad por el id.")
    public ResponseEntity<UnidadEntity> obtenerUnidadPorId(
            @Parameter
                    (
                            description = "Campo que contiene el id de la empresa a buscar.",
                            required = true
                    ) @PathVariable("unidadId") Long unidadId
    ) {
        var unidadEntidad = unidadService.obtenerUnidadPorId(unidadId);

        if (unidadEntidad == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok((UnidadEntity) unidadEntidad);
    }

    @GetMapping(produces = "application/json")
    @Operation(summary = "Servicio para obtener las entidades de unidad.")
    public ResponseEntity<List<UnidadEntity>> obtenerUnidades() {
        var listaUnidades = unidadService.obtenerUnidades();
        return new ResponseEntity<>(listaUnidades, HttpStatus.OK);
    }

}
