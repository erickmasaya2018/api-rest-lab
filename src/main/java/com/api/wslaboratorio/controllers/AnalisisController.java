package com.api.wslaboratorio.controllers;


import com.api.wslaboratorio.dto.AnalisisDto;
import com.api.wslaboratorio.dto.salida.AnalisisSalidaDto;
import com.api.wslaboratorio.dto.salida.GrupoSalidaDto;
import com.api.wslaboratorio.dto.salida.UnidadSalidaDto;
import com.api.wslaboratorio.entities.AnalisisEntity;
import com.api.wslaboratorio.services.IAnalisisService;
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
import java.util.ArrayList;
import java.util.List;

@Tag(name = "analisis", description = "Controlador para gestionar la entidad de analisis.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/analisis")
public class AnalisisController {

    private final IAnalisisService analisisService;

    @PostMapping(produces = "application/json")
    @Operation(summary = "Servicio para crear la entidad analisis.")
    public ResponseEntity<AnalisisSalidaDto> crearAnalisis(@RequestBody @Valid AnalisisDto analisisDto, HttpServletRequest request) {
        UnidadSalidaDto unidadSalidaDto = null;
        GrupoSalidaDto grupoSalidaDto = null;

        var nuevaEmpleado = analisisService.crearAnalisis(analisisDto, request);
        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(nuevaEmpleado.getAnalisisId()).toUri();

        if (nuevaEmpleado.getUnidadEntity() != null) {
            unidadSalidaDto = UnidadSalidaDto.builder()
                    .unidadId(nuevaEmpleado.getUnidadEntity().getUnidadId())
                    .nombre(nuevaEmpleado.getUnidadEntity().getNombre())
                    .observacion(nuevaEmpleado.getUnidadEntity().getObservacion())
                    .build();
        }

        if (nuevaEmpleado.getGrupoEntity() != null) {
            grupoSalidaDto = GrupoSalidaDto.builder()
                    .grupoId(nuevaEmpleado.getGrupoEntity().getGrupoId())
                    .nombre(nuevaEmpleado.getGrupoEntity().getNombre())
                    .observacion(nuevaEmpleado.getGrupoEntity().getObservacion())
                    .build();
        }

        AnalisisSalidaDto analisisSalidaDto = AnalisisSalidaDto.builder()
                .analisisId(nuevaEmpleado.getAnalisisId())
                .nombre(nuevaEmpleado.getNombre())
                .precio(nuevaEmpleado.getPrecio())
                .maximo(nuevaEmpleado.getMaximo())
                .minimo(nuevaEmpleado.getMinimo())
                .unidad(unidadSalidaDto)
                .grupo(grupoSalidaDto)
                .build();

        return ResponseEntity.created(ubicacion).body(analisisSalidaDto);
    }

    @PutMapping(value = "/{analisisId}", produces = "application/json")
    @Operation(summary = "Servicio para editar la entidad analisis.")
    public ResponseEntity<AnalisisSalidaDto> editarAnalisis(
            @Parameter
                    (
                            description = "Campo que contiene el id del empleado a editar.",
                            required = true
                    ) @PathVariable("analisisId") Long analisisId,
            @RequestBody @Valid AnalisisDto analisisDto, HttpServletRequest request) {
        UnidadSalidaDto unidadSalidaDto = null;
        GrupoSalidaDto grupoSalidaDto = null;

        AnalisisEntity empleadoActualizado = analisisService.editarAnalisis(analisisId, analisisDto, request);
        if (empleadoActualizado == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        if (empleadoActualizado.getUnidadEntity() != null) {
            unidadSalidaDto = UnidadSalidaDto.builder()
                    .unidadId(empleadoActualizado.getUnidadEntity().getUnidadId())
                    .nombre(empleadoActualizado.getUnidadEntity().getNombre())
                    .observacion(empleadoActualizado.getUnidadEntity().getObservacion())
                    .build();
        }

        if (empleadoActualizado.getGrupoEntity() != null) {
            grupoSalidaDto = GrupoSalidaDto.builder()
                    .grupoId(empleadoActualizado.getGrupoEntity().getGrupoId())
                    .nombre(empleadoActualizado.getGrupoEntity().getNombre())
                    .observacion(empleadoActualizado.getGrupoEntity().getObservacion())
                    .build();
        }

        AnalisisSalidaDto analisisSalidaDto = AnalisisSalidaDto.builder()
                .analisisId(empleadoActualizado.getAnalisisId())
                .nombre(empleadoActualizado.getNombre())
                .precio(empleadoActualizado.getPrecio())
                .maximo(empleadoActualizado.getMaximo())
                .minimo(empleadoActualizado.getMinimo())
                .unidad(unidadSalidaDto)
                .grupo(grupoSalidaDto)
                .build();

        return ResponseEntity.ok(analisisSalidaDto);
    }

    @DeleteMapping(value = "/{analisisId}", produces = "application/json")
    @Operation(summary = "Servicio para eliminar la entidad analisis.")
    public ResponseEntity<AnalisisEntity> eliminarAnalisis(
            @Parameter
                    (
                            description = "Campo que contiene el id del empleado a eliminar.",
                            required = true
                    ) @PathVariable("analisisId") Long analisisId
    ) {
        String empleadoEliminado = analisisService.eliminarAnalisis(analisisId);

        if (empleadoEliminado == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        if (empleadoEliminado != null) {
            if (empleadoEliminado.equalsIgnoreCase("eliminado")) {

                return ResponseEntity.noContent().build();
            }
        }

        return null;
    }

    @GetMapping(value = "/{analisisId}", produces = "application/json")
    @Operation(summary = "Servicio para obtener la entidad analisis por el id.")
    public ResponseEntity<AnalisisSalidaDto> obtenerAnalisisPorId(
            @Parameter
                    (
                            description = "Campo que contiene el id de la empleado a buscar.",
                            required = true
                    ) @PathVariable("analisisId") Long analisisId
    ) {
        AnalisisEntity analisisEntity = analisisService.obtenerAnalisisPorId(analisisId);
        UnidadSalidaDto unidadSalidaDto = null;
        GrupoSalidaDto grupoSalidaDto = null;

        if (analisisEntity == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        if (analisisEntity.getUnidadEntity() != null) {
            unidadSalidaDto = UnidadSalidaDto.builder()
                    .unidadId(analisisEntity.getUnidadEntity().getUnidadId())
                    .nombre(analisisEntity.getUnidadEntity().getNombre())
                    .observacion(analisisEntity.getUnidadEntity().getObservacion())
                    .build();
        }

        if (analisisEntity.getGrupoEntity() != null) {
            grupoSalidaDto = GrupoSalidaDto.builder()
                    .grupoId(analisisEntity.getGrupoEntity().getGrupoId())
                    .nombre(analisisEntity.getGrupoEntity().getNombre())
                    .observacion(analisisEntity.getGrupoEntity().getObservacion())
                    .build();
        }

        AnalisisSalidaDto analisisSalidaDto = AnalisisSalidaDto.builder()
                .analisisId(analisisEntity.getAnalisisId())
                .nombre(analisisEntity.getNombre())
                .precio(analisisEntity.getPrecio())
                .maximo(analisisEntity.getMaximo())
                .minimo(analisisEntity.getMinimo())
                .unidad(unidadSalidaDto)
                .grupo(grupoSalidaDto)
                .build();

        return ResponseEntity.ok(analisisSalidaDto);
    }

    @GetMapping(produces = "application/json")
    @Operation(summary = "Servicio para obtener las entidades de analisis.")
    public ResponseEntity<List<AnalisisSalidaDto>> obtenerAnalisis() {

        List<AnalisisEntity> listaAnalisis = analisisService.obtenerAnalisis();
        List<AnalisisSalidaDto> lstAnalisisDto = new ArrayList<>();

        listaAnalisis.forEach(itemAnalisis -> {
            UnidadSalidaDto unidadSalidaDto = null;
            GrupoSalidaDto grupoSalidaDto = null;

            if (itemAnalisis.getUnidadEntity() != null) {
                unidadSalidaDto = UnidadSalidaDto.builder()
                        .unidadId(itemAnalisis.getUnidadEntity().getUnidadId())
                        .nombre(itemAnalisis.getUnidadEntity().getNombre())
                        .observacion(itemAnalisis.getUnidadEntity().getObservacion())
                        .build();
            }

            if (itemAnalisis.getGrupoEntity() != null) {
                grupoSalidaDto = GrupoSalidaDto.builder()
                        .grupoId(itemAnalisis.getGrupoEntity().getGrupoId())
                        .nombre(itemAnalisis.getGrupoEntity().getNombre())
                        .observacion(itemAnalisis.getGrupoEntity().getObservacion())
                        .build();
            }

            AnalisisSalidaDto analisisSalidaDto = AnalisisSalidaDto.builder()
                    .analisisId(itemAnalisis.getAnalisisId())
                    .nombre(itemAnalisis.getNombre())
                    .precio(itemAnalisis.getPrecio())
                    .maximo(itemAnalisis.getMaximo())
                    .minimo(itemAnalisis.getMinimo())
                    .unidad(unidadSalidaDto)
                    .grupo(grupoSalidaDto)
                    .build();

            lstAnalisisDto.add(analisisSalidaDto);
        });

        return new ResponseEntity<>(lstAnalisisDto, HttpStatus.OK);
    }

}
