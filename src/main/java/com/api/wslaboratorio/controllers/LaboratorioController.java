package com.api.wslaboratorio.controllers;


import com.api.wslaboratorio.dto.LaboratorioDto;
import com.api.wslaboratorio.entities.LaboratorioEntity;
import com.api.wslaboratorio.services.ILaboratorioService;
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

@Tag(name = "laboratorio", description = "Controlador para gestionar la entidad de laboratorio.")
@RestController
@RequiredArgsConstructor
@RequestMapping("${api.base}/api/${api.version}/laboratorio")
public class LaboratorioController {

    private final ILaboratorioService laboratorioService;

    @PostMapping(produces = "application/json")
    @Operation(summary = "Servicio para crear la entidad laboratorio.")
    public ResponseEntity<LaboratorioEntity> crearLaboratorio(@RequestBody @Valid LaboratorioDto laboratorioDto, HttpServletRequest request) {
        var laboratorioNuevo = laboratorioService.crearLaboratorio(laboratorioDto, request);
        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(laboratorioNuevo.getLaboratorioId()).toUri();

        return ResponseEntity.created(ubicacion).body(laboratorioNuevo);
    }

    @PutMapping(produces = "application/json")
    @Operation(summary = "Servicio para editar la entidad laboratorio.")
    public ResponseEntity<LaboratorioEntity> editarLaboratorio(
            @Parameter
                    (
                            description = "Campo que contiene el id del laboratorio a editar.",
                            required = true
                    ) Long id,
            @RequestBody @Valid LaboratorioDto laboratorioDto, HttpServletRequest request) {
        var laboratorioActualizado = laboratorioService.editarLaboratorio(id, laboratorioDto, request);
        if (laboratorioActualizado == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(laboratorioActualizado);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Servicio para eliminar la entidad laboratorio.")
    public ResponseEntity<LaboratorioEntity> eliminarLaboratorio(
            @Parameter
                    (
                            description = "Campo que contiene el id del laboratorio a eliminar.",
                            required = true
                    ) Long id
            , HttpServletRequest request) {
        String laboratorioEliminado = laboratorioService.eliminarLaboratorio(id);

        if (laboratorioEliminado == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        if (laboratorioEliminado != null) {
            if (laboratorioEliminado.equalsIgnoreCase("eliminado")) {

                return ResponseEntity.noContent().build();
            }
        }

        return null;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Servicio para obtener la entidad laboratorio por id.")
    public ResponseEntity<LaboratorioEntity> obtenerLaboratorioPorId(
            @Parameter
                    (
                            description = "Campo que contiene el id del laboratorio a buscar.",
                            required = true
                    ) Long id
    ) {
        var laboratorioEntidad = laboratorioService.obtenerLaboratorioPorId(id);

        if (laboratorioEntidad == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok((LaboratorioEntity) laboratorioEntidad);
    }

    @GetMapping(produces = "application/json")
    @Operation(summary = "Servicio para obtener una lista de entidades de laboratorio.")
    public ResponseEntity<LaboratorioEntity> obtenerLaboratorios(Pageable pageable) {
        var listaLaboratorios = laboratorioService.obtenerLaboratorios(pageable);
        return ResponseEntity.ok((LaboratorioEntity) listaLaboratorios);
    }

}
