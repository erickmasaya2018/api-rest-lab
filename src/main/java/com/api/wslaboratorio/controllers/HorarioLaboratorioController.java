package com.api.wslaboratorio.controllers;


import com.api.wslaboratorio.dto.HorarioLaborarioEntityDto;
import com.api.wslaboratorio.entities.HorarioLaborarioEntity;
import com.api.wslaboratorio.services.IHorarioLaboratorioService;
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

@Tag(name = "horario-laboratorio", description = "Controlador para gestionar el horario de atención de un laboratorio.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/horariolaboratorio")
public class HorarioLaboratorioController {

    private final IHorarioLaboratorioService horarioLaboratorioService;

    @PostMapping(produces = "application/json")
    @Operation(summary = "Servicio para crear la entidad horario laboratorio.")
    public ResponseEntity<HorarioLaborarioEntity> crearHorarioLaboratorio(@RequestBody @Valid HorarioLaborarioEntityDto horarioLaborarioEntityDto, HttpServletRequest request) {
        var horarioLaboratorioNuevo = horarioLaboratorioService.crearHorarioLaboratorio(horarioLaborarioEntityDto, request);
        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(horarioLaboratorioNuevo.getHorarioLaboratorioId()).toUri();

        return ResponseEntity.created(ubicacion).body(horarioLaboratorioNuevo);
    }

    @PutMapping(value = "/{horarioLaboratorioId}", produces = "application/json")
    @Operation(summary = "Servicio para editar la entidad horario laboratorio.")
    public ResponseEntity<HorarioLaborarioEntity> editarHorarioLaboratorio(@Parameter(description = "Campo que contiene el id del laboratorio a editar.", required = true) @PathVariable("horarioLaboratorioId") Long horarioLaboratorioId, @RequestBody @Valid HorarioLaborarioEntityDto horarioLaborarioEntityDto, HttpServletRequest request) {
        var horarioLaborarioActualizado = horarioLaboratorioService.editarHorarioLaboratorio(horarioLaboratorioId, horarioLaborarioEntityDto, request);
        if (horarioLaborarioActualizado == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(horarioLaborarioActualizado);
    }

    @DeleteMapping(value = "/{horarioLaboratorioId}", produces = "application/json")
    @Operation(summary = "Servicio para eliminar la entidad horario laboratorio.")
    public ResponseEntity<HorarioLaborarioEntity> eliminarLaboratorio(@Parameter(description = "Campo que contiene el id del laboratorio a eliminar.", required = true) @PathVariable("horarioLaboratorioId") Long horarioLaboratorioId, HttpServletRequest request) {
        String horarioLaboratorioEliminado = horarioLaboratorioService.eliminarHorarioLaboratorio(horarioLaboratorioId);

        if (horarioLaboratorioEliminado == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        if (horarioLaboratorioEliminado != null) {
            if (horarioLaboratorioEliminado.equalsIgnoreCase("eliminado")) {

                return ResponseEntity.noContent().build();
            }
        }

        return null;
    }

    @GetMapping(value = "/{horarioLaboratorioId}", produces = "application/json")
    @Operation(summary = "Servicio para obtener la entidad horario laboratorio por id.")
    public ResponseEntity<HorarioLaborarioEntity> obtenerLaboratorioPorId(@Parameter(description = "Campo que contiene el id del laboratorio a buscar.", required = true) @PathVariable("horarioLaboratorioId") Long horarioLaboratorioId) {
        var horariolaboratorioEntidad = horarioLaboratorioService.obtenerHorarioLaboratorioPorId(horarioLaboratorioId);

        if (horariolaboratorioEntidad == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok((HorarioLaborarioEntity) horariolaboratorioEntidad);
    }

    @GetMapping(produces = "application/json")
    @Operation(summary = "Servicio para obtener una lista de entidades de horario laboratorio.")
    public ResponseEntity<List<HorarioLaborarioEntity>> obtenerLaboratorios() {
        var listaHorarioLaboratorio = horarioLaboratorioService.obtenerHorarioLaboratorios();
        return new ResponseEntity<>(listaHorarioLaboratorio, HttpStatus.OK);
    }

}
