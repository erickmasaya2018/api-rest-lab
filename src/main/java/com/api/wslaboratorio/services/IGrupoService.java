package com.api.wslaboratorio.services;

import com.api.wslaboratorio.dto.GrupoDto;
import com.api.wslaboratorio.entities.GrupoEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IGrupoService {
    GrupoEntity crearGrupo(GrupoDto grupoDto, HttpServletRequest request);

    GrupoEntity editarGrupo(Long id, GrupoDto grupoDto, HttpServletRequest request);

    String eliminarGrupo(Long id);

    Iterable<GrupoEntity> obtenerGrupoPorId(Long id);

    Page<GrupoEntity> obtenerGrupos(Pageable pageable);

}
