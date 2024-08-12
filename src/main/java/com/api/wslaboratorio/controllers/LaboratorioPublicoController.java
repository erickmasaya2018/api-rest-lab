package com.api.wslaboratorio.controllers;


import com.api.wslaboratorio.dto.salida.LaboratorioPublicoSalidaDto;
import com.api.wslaboratorio.services.ILaboratorioPublicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "laboratorio-publico", description = "Controlador para gestionar los laboratorios que son parte de la empresa.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/laboratorio-publico")
public class LaboratorioPublicoController {

    private final ILaboratorioPublicoService laboratorioPublicoService;

    @GetMapping(produces = "application/json")
    @Operation(summary = "Obtener todos los laboratorios para el publico en general.")
    public List<LaboratorioPublicoSalidaDto> obtenerLaboratorioPublicos() {
        var listaLaboratoriosPublico = laboratorioPublicoService.obtenerLaboratorioPublicos();
        return listaLaboratoriosPublico;
    }

}
