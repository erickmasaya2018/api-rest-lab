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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "grupo", description = "Controlador para gestionar la entidad de grupo.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/grupo")
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

    @PutMapping(value = "/{grupoId}", produces = "application/json")
    @Operation(summary = "Servicio para editar la entidad grupo.")
    public ResponseEntity<GrupoEntity> editarGrupo(
            @Parameter
                    (
                            description = "Campo que contiene el id del empresa a editar.",
                            required = true
                    ) @PathVariable("grupoId") Long grupoId,
            @RequestBody @Valid GrupoDto grupoDto, HttpServletRequest request) {
        var grupoActualizado = grupoService.editarGrupo(grupoId, grupoDto, request);
        if (grupoActualizado == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(grupoActualizado);
    }


    @DeleteMapping(value = "/{grupoId}", produces = "application/json")
    @Operation(summary = "Servicio para eliminar la entidad grupo.")
    public ResponseEntity<GrupoEntity> eliminarGrupo(
            @Parameter
                    (
                            description = "Campo que contiene el id del empresa a eliminar.",
                            required = true
                    ) @PathVariable("grupoId") Long grupoId
    ) {
        String grupoEliminado = grupoService.eliminarGrupo(grupoId);

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

    @GetMapping(value = "/{grupoId}", produces = "application/json")
    @Operation(summary = "Servicio para obtener la entidad grupo por el id.")
    public ResponseEntity<GrupoEntity> obtenerGrupoPorId(
            @Parameter
                    (
                            description = "Campo que contiene el id de la empresa a buscar.",
                            required = true
                    ) @PathVariable("grupoId") Long grupoId
    ) {
        var grupoEntidad = grupoService.obtenerGrupoPorId(grupoId);

        if (grupoEntidad == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok((GrupoEntity) grupoEntidad);
    }

    @GetMapping(produces = "application/json")
    @Operation(summary = "Servicio para obtener las entidades de grupo.")
    public ResponseEntity<List<GrupoEntity>> obtenerGrupos() {
        var listaGrupos = grupoService.obtenerGrupos();
        return new ResponseEntity<>(listaGrupos, HttpStatus.OK);
    }

}
