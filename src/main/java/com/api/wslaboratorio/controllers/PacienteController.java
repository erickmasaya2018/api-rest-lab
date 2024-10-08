package com.api.wslaboratorio.controllers;


import com.api.wslaboratorio.dto.PacienteDto;
import com.api.wslaboratorio.dto.custom.PacienteCustomDto;
import com.api.wslaboratorio.entities.PacienteEntity;
import com.api.wslaboratorio.services.IPacienteService;
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

@Tag(name = "paciente", description = "Controlador para gestionar la entidad de paciente.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/paciente")
public class PacienteController {

    private final IPacienteService pacienteService;

    @PostMapping(produces = "application/json")
    @Operation(summary = "Servicio para crear la entidad empresa.")
    public ResponseEntity<PacienteEntity> crearPaciente(@RequestBody @Valid PacienteDto pacienteDto, HttpServletRequest request) {
        var pacienteNuevo = pacienteService.crearPaciente(pacienteDto, request);
        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(pacienteNuevo.getPacienteId()).toUri();

        return ResponseEntity.created(ubicacion).body(pacienteNuevo);
    }

    @PutMapping(value = "/{pacienteId}", produces = "application/json")
    @Operation(summary = "Servicio para editar la entidad empresa.")
    public ResponseEntity<PacienteEntity> editarPaciente(
            @Parameter
                    (
                            description = "Campo que contiene el id del empresa a editar.",
                            required = true
                    ) @PathVariable("pacienteId") Long pacienteId,
            @RequestBody @Valid PacienteDto pacienteDto, HttpServletRequest request) {
        var pacienteActualizado = pacienteService.editarPaciente(pacienteId, pacienteDto, request);
        if (pacienteActualizado == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(pacienteActualizado);
    }


    @DeleteMapping(value = "/{pacienteId}", produces = "application/json")
    @Operation(summary = "Servicio para eliminar la entidad empresa.")
    public ResponseEntity<PacienteEntity> eliminarPaciente(
            @Parameter
                    (
                            description = "Campo que contiene el id del empresa a eliminar.",
                            required = true
                    ) @PathVariable("pacienteId") Long pacienteId
    ) {
        String pacienteEliminado = pacienteService.eliminarPaciente(pacienteId);

        if (pacienteEliminado == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        if (pacienteEliminado != null) {
            if (pacienteEliminado.equalsIgnoreCase("eliminado")) {

                return ResponseEntity.noContent().build();
            }
        }

        return null;
    }

    @GetMapping(value = "/{pacienteId}", produces = "application/json")
    @Operation(summary = "Servicio para obtener la entidad empresa por el id.")
    public ResponseEntity<PacienteEntity> obtenerPacientePorId(
            @Parameter
                    (
                            description = "Campo que contiene el id de la empresa a buscar.",
                            required = true
                    ) @PathVariable("pacienteId") Long pacienteId
    ) {
        var pacienteEntidad = pacienteService.obtenerPacientePorId(pacienteId);

        if (pacienteEntidad == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok((PacienteEntity) pacienteEntidad);
    }

    @GetMapping(produces = "application/json")
    @Operation(summary = "Servicio para obtener las entidades de empresa.")
    public ResponseEntity<List<PacienteEntity>> obtenerPacientes() {
        var listaPacientes = pacienteService.obtenerPacientes();
        return new ResponseEntity<>(listaPacientes, HttpStatus.OK);
    }


    @GetMapping(value = "/{pacienteId}/analisis", produces = "application/json")
    @Operation(summary = "Servicio para obtener los análisis que corresponden al id de un paciente.")
    public ResponseEntity<PacienteCustomDto> obtenerAnalisisPorPacienteId(
            @Parameter
                    (
                            description = "Campo que contiene el id del empresa a eliminar.",
                            required = true
                    ) Long pacienteId) {
        var listaAnalisis = pacienteService.obtenerAnalisisPorPacienteId(pacienteId);
        return ResponseEntity.ok((PacienteCustomDto) listaAnalisis);
    }

}
