package com.api.wslaboratorio.controllers;


import com.api.wslaboratorio.dto.EmpresaDto;
import com.api.wslaboratorio.dto.salida.EmpresaSalidaDto;
import com.api.wslaboratorio.entities.EmpresaEntity;
import com.api.wslaboratorio.services.IEmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "empresa", description = "Controlador para gestionar la entidad de empresa.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/empresa")
public class EmpresaController {

    private final IEmpresaService empresaService;
    private final ModelMapper modelMapper;

    @PutMapping(value = "/{empresaId}", produces = "application/json")
    @Operation(summary = "Servicio para editar la entidad empresa.")
    public ResponseEntity<EmpresaSalidaDto> editarEmpresa(
            @Parameter
                    (
                            description = "Campo que contiene el id del empresa a editar.",
                            required = true
                    ) @PathVariable("empresaId") Long empresaId,
            @RequestBody
            @Valid EmpresaDto empresaDto, HttpServletRequest
                    request) {

        EmpresaEntity empresaActualizada = empresaService.editarEmpresa(empresaId, empresaDto, request);

        if (empresaActualizada == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(modelMapper.map(empresaActualizada, EmpresaSalidaDto.class));

    }

    @PostMapping(produces = "application/json")
    @Operation(summary = "Servicio para crear la entidad empresa.")
    public ResponseEntity<EmpresaSalidaDto> crearEmpresa(@RequestBody @Valid EmpresaDto empresaDto, HttpServletRequest request) {
        EmpresaEntity nuevaEmpresa = empresaService.crearEmpresa(empresaDto, request);
        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(nuevaEmpresa.getEmpresaId()).toUri();

        return ResponseEntity.created(ubicacion).body(modelMapper.map(nuevaEmpresa, EmpresaSalidaDto.class));
    }

    @DeleteMapping(value = "/{empresaId}", produces = "application/json")
    @Operation(summary = "Servicio para eliminar la entidad empresa.")
    public ResponseEntity<EmpresaEntity> eliminarEmpresa(
            @Parameter
                    (
                            description = "Campo que contiene el id del empresa a eliminar.",
                            required = true
                    ) @PathVariable("empresaId") Long empresaId
    ) {
        String empresaEliminada = empresaService.eliminarEmpresa(empresaId);

        if (empresaEliminada == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        if (empresaEliminada != null) {
            if (empresaEliminada.equalsIgnoreCase("eliminado")) {

                return ResponseEntity.noContent().build();
            }
        }

        return null;
    }

    @GetMapping(value = "/{empresaId}", produces = "application/json")
    @Operation(summary = "Servicio para obtener la entidad empresa por el id.")
    public ResponseEntity<EmpresaSalidaDto> obtenerEmpresaPorId(
            @Parameter
                    (
                            description = "Campo que contiene el id de la empresa a buscar.",
                            required = true
                    ) @PathVariable("empresaId") Long empresaId
    ) {
        EmpresaEntity empresaEntidad = empresaService.obtenerEmpresaPorId(empresaId);
        return new ResponseEntity<>(modelMapper.map(empresaEntidad, EmpresaSalidaDto.class), HttpStatus.OK);
    }

    @GetMapping(produces = "application/json")
    @Operation(summary = "Servicio para obtener las entidades de empresa.")
    public ResponseEntity<List<EmpresaSalidaDto>> obtenerEmpresas() {
        List<EmpresaEntity> listaEmpresas = empresaService.obtenerEmpresas();
        return new ResponseEntity<>(listaEmpresas.stream().map((element) -> modelMapper.map(element, EmpresaSalidaDto.class)).collect(Collectors.toList()), HttpStatus.OK);
    }

}
