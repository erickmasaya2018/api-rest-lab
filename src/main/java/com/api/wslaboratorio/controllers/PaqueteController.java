package com.api.wslaboratorio.controllers;


import com.api.wslaboratorio.dto.PaqueteDto;
import com.api.wslaboratorio.entities.PaqueteEntity;
import com.api.wslaboratorio.services.IPaqueteService;
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

@Tag(name = "paquete", description = "Controlador para gestionar la entidad de paquete.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/paquete")
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

    @PutMapping(value = "/{paqueteId}", produces = "application/json")
    @Operation(summary = "Servicio para editar la entidad paquete.")
    public ResponseEntity<PaqueteEntity> editarPaquete(
            @Parameter
                    (
                            description = "Campo que contiene el id del empresa a editar.",
                            required = true
                    ) @PathVariable("paqueteId") Long id,
            @RequestBody @Valid PaqueteDto paqueteDto, HttpServletRequest request) {
        var paqueteActualizado = paqueteService.editarPaquete(id, paqueteDto, request);
        if (paqueteActualizado == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(paqueteActualizado);
    }


    @DeleteMapping(value = "/{paqueteId}", produces = "application/json")
    @Operation(summary = "Servicio para eliminar la entidad paquete.")
    public ResponseEntity<PaqueteEntity> eliminarPaquete(
            @Parameter
                    (
                            description = "Campo que contiene el id del empresa a eliminar.",
                            required = true
                    )@PathVariable("paqueteId") Long paqueteId
    ) {
        String paqueteEliminado = paqueteService.eliminarPaquete(paqueteId);

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

    @GetMapping(value = "/{paqueteId}", produces = "application/json")
    @Operation(summary = "Servicio para obtener la entidad paquete por el id.")
    public ResponseEntity<PaqueteEntity> obtenerPaquetePorId(
            @Parameter
                    (
                            description = "Campo que contiene el id de la empresa a buscar.",
                            required = true
                    ) @PathVariable("paqueteId") Long paqueteId
    ) {
        var paqueteEntidad = paqueteService.obtenerPaquetePorId(paqueteId);

        if (paqueteEntidad == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(paqueteEntidad);
    }

    @GetMapping(produces = "application/json")
    @Operation(summary = "Servicio para obtener las entidades de paquete.")
    public ResponseEntity<List<PaqueteEntity>> obtenerPaquetes() {
        var listaPaquetes = paqueteService.obtenerPaquetes();
        return new ResponseEntity<>(listaPaquetes, HttpStatus.OK);
    }

}
