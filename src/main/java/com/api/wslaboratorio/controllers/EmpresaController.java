package com.api.wslaboratorio.controllers;


import com.api.wslaboratorio.dto.EmpresaDto;
import com.api.wslaboratorio.entities.EmpresaEntity;
import com.api.wslaboratorio.services.IEmpresaService;
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

@Tag(name = "empresa", description = "Controlador para gestionar la entidad de empresa.")
@RestController
@RequiredArgsConstructor
@RequestMapping("${api.base}/api/${api.version}/empresa")
public class EmpresaController {

    private final IEmpresaService empresaService;

    @PostMapping(produces = "application/json")
    @Operation(summary = "Servicio para crear la entidad empresa.")
    public ResponseEntity<EmpresaEntity> crearEmpresa(@RequestBody @Valid EmpresaDto empresaDto, HttpServletRequest request) {
        var nuevaEmpresa = empresaService.crearEmpresa(empresaDto, request);
        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(nuevaEmpresa.getEmpresaId()).toUri();

        return ResponseEntity.created(ubicacion).body(nuevaEmpresa);
    }

    @PutMapping(produces = "application/json")
    @Operation(summary = "Servicio para editar la entidad empresa.")
    public ResponseEntity<EmpresaEntity> editarEmpresa(
            @Parameter
                    (
                            description = "Campo que contiene el id del empresa a editar.",
                            required = true
                    ) Long id,
            @RequestBody @Valid EmpresaDto empresaDto, HttpServletRequest request) {
        var empresaActualizada = empresaService.editarEmpresa(id, empresaDto, request);
        if (empresaActualizada == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(empresaActualizada);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Servicio para eliminar la entidad empresa.")
    public ResponseEntity<EmpresaEntity> eliminarEmpresa(
            @Parameter
                    (
                            description = "Campo que contiene el id del empresa a eliminar.",
                            required = true
                    ) Long id
    ) {
        String empresaEliminada = empresaService.eliminarEmpresa(id);

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

    @GetMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Servicio para obtener la entidad empresa por el id.")
    public ResponseEntity<EmpresaEntity> obtenerEmpresaPorId(
            @Parameter
                    (
                            description = "Campo que contiene el id de la empresa a buscar.",
                            required = true
                    ) Long id
    ) {
        var empresaEntidad = empresaService.obtenerEmpresaPorId(id);

        if (empresaEntidad == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok((EmpresaEntity) empresaEntidad);
    }

    @GetMapping(produces = "application/json")
    @Operation(summary = "Servicio para obtener las entidades de empresa.")
    public ResponseEntity<EmpresaEntity> obtenerEmpresas(Pageable pageable) {
        var listaEmpresas = empresaService.obtenerEmpresas(pageable);
        return ResponseEntity.ok((EmpresaEntity) listaEmpresas);
    }

}
