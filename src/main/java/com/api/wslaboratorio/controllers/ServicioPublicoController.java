package com.api.wslaboratorio.controllers;


import com.api.wslaboratorio.dto.salida.ServicioPublicoSalidaDto;
import com.api.wslaboratorio.services.IServicioPublicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "servicio-publico", description = "Controlador para gestionar la cartera de servicios del laboratorio.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/servicios-publico")
public class ServicioPublicoController {

    private final IServicioPublicoService servicioPublicoService;

    @GetMapping(produces = "application/json")
    @Operation(summary = "Obtener todos los servicios para el publico en general.")
    public List<ServicioPublicoSalidaDto> obtenerServiciosPublicos() {
        var listaServiciosPublico = servicioPublicoService.obtenerServiciosPublicos();
        return listaServiciosPublico;
    }

}
