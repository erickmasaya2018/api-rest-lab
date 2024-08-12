package com.api.wslaboratorio.services;

import com.api.wslaboratorio.dto.GrupoDto;
import com.api.wslaboratorio.entities.GrupoEntity;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface IGrupoService {
    GrupoEntity crearGrupo(GrupoDto grupoDto, HttpServletRequest request);

    GrupoEntity editarGrupo(Long id, GrupoDto grupoDto, HttpServletRequest request);

    String eliminarGrupo(Long id);

    GrupoEntity obtenerGrupoPorId(Long id);

    List<GrupoEntity> obtenerGrupos();

}
