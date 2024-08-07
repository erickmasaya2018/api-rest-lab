package com.api.wslaboratorio.controllers;


import com.api.wslaboratorio.dto.PaqueteDto;
import com.api.wslaboratorio.entities.EmpresaEntity;
import com.api.wslaboratorio.entities.PaqueteEntity;
import com.api.wslaboratorio.services.IPaqueteService;
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

@Tag(name = "paquete", description = "Controlador para gestionar la entidad de paquete.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/${api.path}/paquete")
public class PaqueteController {

    private final IPaqueteService paqueteService;

    @PostMapping(produces = "application/json")
    @Operation(summary = "Servicio para crear la entidad paquete.")
    public ResponseEntity<PaqueteEntity> crearPaquete(@RequestBody @Valid PaqueteDto paqueteDto, HttpServletRequest request) {
        var paqueteNuevo = paqueteService.crearPaquete(paqueteDto, request);
        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(paqueteNuevo.getPaqueteId()).toUri();

        return ResponseEntity.created(ubicacion).body(paqueteNuevo);
    }

    @PutMapping(produces = "application/json")
    @Operation(summary = "Servicio para editar la entidad paquete.")
    public ResponseEntity<PaqueteEntity> editarPaquete(
            @Parameter
                    (
                            description = "Campo que contiene el id del empresa a editar.",
                            required = true
                    ) Long id,
            @RequestBody @Valid PaqueteDto paqueteDto, HttpServletRequest request) {
        var paqueteActualizado = paqueteService.editarPaquete(id, paqueteDto, request);
        if (paqueteActualizado == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(paqueteActualizado);
    }


    @DeleteMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Servicio para eliminar la entidad paquete.")
    public ResponseEntity<PaqueteEntity> eliminarPaquete(
            @Parameter
                    (
                            description = "Campo que contiene el id del empresa a eliminar.",
                            required = true
                    ) Long id
    ) {
        String paqueteEliminado = paqueteService.eliminarPaquete(id);

        if (paqueteEliminado == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        if (paqueteEliminado != null) {
            if (paqueteEliminado.equalsIgnoreCase("eliminado")) {

                return ResponseEntity.noContent().build();
            }
        }

        return null;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Servicio para obtener la entidad paquete por el id.")
    public ResponseEntity<EmpresaEntity> obtenerPaquetePorId(
            @Parameter
                    (
                            description = "Campo que contiene el id de la empresa a buscar.",
                            required = true
                    ) Long id
    ) {
        var paqueteEntidad = paqueteService.obtenerPaquetePorId(id);

        if (paqueteEntidad == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok((EmpresaEntity) paqueteEntidad);
    }

    @GetMapping(produces = "application/json")
    @Operation(summary = "Servicio para obtener las entidades de paquete.")
    public ResponseEntity<PaqueteEntity> obtenerPaquetes(Pageable pageable) {
        var listaPaquetes = paqueteService.obtenerPaquetes(pageable);
        return ResponseEntity.ok((PaqueteEntity) listaPaquetes);
    }

}
