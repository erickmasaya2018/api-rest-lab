package com.api.wslaboratorio.controllers;


import com.api.wslaboratorio.dto.GeneroDto;
import com.api.wslaboratorio.entities.GeneroEntity;
import com.api.wslaboratorio.services.IGeneroService;
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

@Tag(name = "genero", description = "Controlador para gestionar la entidad de genero.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/genero")
public class GeneroController {

    private final IGeneroService generoService;

    @PostMapping(produces = "application/json")
    @Operation(summary = "Servicio para crear la entidad genero.")
    public ResponseEntity<GeneroEntity> crearGenero(@RequestBody @Valid GeneroDto generoDto, HttpServletRequest request) {
        var nuevaGenero = generoService.crearGenero(generoDto, request);
        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(nuevaGenero.getGeneroId()).toUri();

        return ResponseEntity.created(ubicacion).body(nuevaGenero);
    }

    @PutMapping(value = "/{generoId}", produces = "application/json")
    @Operation(summary = "Servicio para editar la entidad genero.")
    public ResponseEntity<GeneroEntity> editarGenero(
            @Parameter
                    (
                            description = "Campo que contiene el id del genero a editar.",
                            required = true
                    ) @PathVariable("generoId") Long generoId,
            @RequestBody @Valid GeneroDto generoDto, HttpServletRequest request) {
        var generoActualizado = generoService.editarGenero(generoId, generoDto, request);
        if (generoActualizado == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(generoActualizado);
    }

    @DeleteMapping(value = "/{generoId}", produces = "application/json")
    @Operation(summary = "Servicio para eliminar la entidad genero.")
    public ResponseEntity<GeneroEntity> eliminarGenero(
            @Parameter
                    (
                            description = "Campo que contiene el id del empresa a eliminar.",
                            required = true
                    ) @PathVariable("generoId") Long generoId
    ) {
        String generoEliminado = generoService.eliminarGenero(generoId);

        if (generoEliminado == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        if (generoEliminado != null) {
            if (generoEliminado.equalsIgnoreCase("eliminado")) {

                return ResponseEntity.noContent().build();
            }
        }

        return null;
    }

    @GetMapping(value = "/{generoId}", produces = "application/json")
    @Operation(summary = "Servicio para obtener la entidad genero por el id.")
    public ResponseEntity<GeneroEntity> obtenerGeneroPorId(
            @Parameter
                    (
                            description = "Campo que contiene el id de la empresa a buscar.",
                            required = true
                    ) @PathVariable("generoId") Long generoId
    ) {
        var generoEntidad = generoService.obtenerGeneroPorId(generoId);

        if (generoEntidad == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok((GeneroEntity) generoEntidad);
    }

    @GetMapping(produces = "application/json")
    @Operation(summary = "Servicio para obtener las entidades de genero.")
    public ResponseEntity<List<GeneroEntity>> obtenerGenero() {
        var listaGeneros = generoService.obtenerGeneros();
        return new ResponseEntity<>(listaGeneros, HttpStatus.OK);
    }

}
