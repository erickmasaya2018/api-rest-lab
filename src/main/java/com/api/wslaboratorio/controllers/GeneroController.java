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
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "genero", description = "Controlador para gestionar la entidad de genero.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/${api.path}/genero")
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

    @PutMapping(produces = "application/json")
    @Operation(summary = "Servicio para editar la entidad genero.")
    public ResponseEntity<GeneroEntity> editarGenero(
            @Parameter
                    (
                            description = "Campo que contiene el id del genero a editar.",
                            required = true
                    ) Long id,
            @RequestBody @Valid GeneroDto generoDto, HttpServletRequest request) {
        var generoActualizado = generoService.editarGenero(id, generoDto, request);
        if (generoActualizado == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(generoActualizado);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Servicio para eliminar la entidad genero.")
    public ResponseEntity<GeneroEntity> eliminarGenero(
            @Parameter
                    (
                            description = "Campo que contiene el id del empresa a eliminar.",
                            required = true
                    ) Long id
    ) {
        String generoEliminado = generoService.eliminarGenero(id);

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

    @GetMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Servicio para obtener la entidad genero por el id.")
    public ResponseEntity<GeneroEntity> obtenerGeneroPorId(
            @Parameter
                    (
                            description = "Campo que contiene el id de la empresa a buscar.",
                            required = true
                    ) Long id
    ) {
        var generoEntidad = generoService.obtenerGeneroPorId(id);

        if (generoEntidad == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok((GeneroEntity) generoEntidad);
    }

    @GetMapping(produces = "application/json")
    @Operation(summary = "Servicio para obtener las entidades de genero.")
    public ResponseEntity<GeneroEntity> obtenerGenero(Pageable pageable) {
        var listaGeneros = generoService.obtenerGeneros(pageable);
        return ResponseEntity.ok((GeneroEntity) listaGeneros);
    }

}
