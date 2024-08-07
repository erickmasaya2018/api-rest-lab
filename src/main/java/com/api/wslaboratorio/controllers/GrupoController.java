package com.api.wslaboratorio.controllers;


import com.api.wslaboratorio.dto.GrupoDto;
import com.api.wslaboratorio.entities.GrupoEntity;
import com.api.wslaboratorio.services.IGrupoService;
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

@Tag(name = "grupo", description = "Controlador para gestionar la entidad de grupo.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/${api.path}/grupo")
public class GrupoController {

    private final IGrupoService grupoService;

    @PostMapping(produces = "application/json")
    @Operation(summary = "Servicio para crear la entidad grupo.")
    public ResponseEntity<GrupoEntity> crearGrupo(@RequestBody @Valid GrupoDto grupoDto, HttpServletRequest request) {
        var nuevoGrupo = grupoService.crearGrupo(grupoDto, request);
        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(nuevoGrupo.getGrupoId()).toUri();

        return ResponseEntity.created(ubicacion).body(nuevoGrupo);
    }

    @PutMapping(produces = "application/json")
    @Operation(summary = "Servicio para editar la entidad grupo.")
    public ResponseEntity<GrupoEntity> editarGrupo(
            @Parameter
                    (
                            description = "Campo que contiene el id del empresa a editar.",
                            required = true
                    ) Long id,
            @RequestBody @Valid GrupoDto grupoDto, HttpServletRequest request) {
        var grupoActualizado = grupoService.editarGrupo(id, grupoDto, request);
        if (grupoActualizado == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(grupoActualizado);
    }


    @DeleteMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Servicio para eliminar la entidad grupo.")
    public ResponseEntity<GrupoEntity> eliminarGrupo(
            @Parameter
                    (
                            description = "Campo que contiene el id del empresa a eliminar.",
                            required = true
                    ) Long id
    ) {
        String grupoEliminado = grupoService.eliminarGrupo(id);

        if (grupoEliminado == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        if (grupoEliminado != null) {
            if (grupoEliminado.equalsIgnoreCase("eliminado")) {

                return ResponseEntity.noContent().build();
            }
        }

        return null;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Servicio para obtener la entidad grupo por el id.")
    public ResponseEntity<GrupoEntity> obtenerGrupoPorId(
            @Parameter
                    (
                            description = "Campo que contiene el id de la empresa a buscar.",
                            required = true
                    ) Long id
    ) {
        var grupoEntidad = grupoService.obtenerGrupoPorId(id);

        if (grupoEntidad == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok((GrupoEntity) grupoEntidad);
    }

    @GetMapping(produces = "application/json")
    @Operation(summary = "Servicio para obtener las entidades de grupo.")
    public ResponseEntity<GrupoEntity> obtenerGrupos(Pageable pageable) {
        var listaGrupos = grupoService.obtenerGrupos(pageable);
        return ResponseEntity.ok((GrupoEntity) listaGrupos);
    }

}
