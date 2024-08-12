package com.api.wslaboratorio.services.imp;

import com.api.wslaboratorio.dto.PaqueteDto;
import com.api.wslaboratorio.entities.AuditoriaEntity;
import com.api.wslaboratorio.entities.GrupoEntity;
import com.api.wslaboratorio.entities.PaqueteEntity;
import com.api.wslaboratorio.repositories.IGrupoRepository;
import com.api.wslaboratorio.repositories.IPaqueteRepository;
import com.api.wslaboratorio.services.IPaqueteService;
import com.api.wslaboratorio.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class PaqueteServiceImpl implements IPaqueteService {

    private final IGrupoRepository grupoRepository;
    private final IPaqueteRepository paqueteRepository;
    private final JwtService jwtService;

    @Override
    public PaqueteEntity crearPaquete(PaqueteDto paqueteDto, HttpServletRequest request) {
        AuditoriaEntity auditoria = AuditoriaEntity.builder()
                .usuarioCreacion(jwtService.extractUsername(request.getHeader("Authorization").substring(7)))
                .fechaCreacion(new Date())
                .build();

        Set<GrupoEntity> grupos = new HashSet<>();

        PaqueteEntity paqueteEntity = PaqueteEntity.builder()
                .nombre(paqueteDto.getNombre())
                .observacion(paqueteDto.getObservacion())
                .porcentaje(paqueteDto.getPorcentaje())
                .grupos(paqueteDto.getGrupos())
                .auditoriaEntity(auditoria)
                .build();

        for (GrupoEntity grupo : paqueteEntity.getGrupos()) {
            GrupoEntity existingGrupo = grupoRepository.findById(grupo.getGrupoId()).orElse(null);
            if (existingGrupo != null) {
                grupos.add(existingGrupo);
            } else {
                grupos.add(grupo);
            }
        }

        paqueteEntity.setGrupos(grupos);
        return paqueteRepository.save(paqueteEntity);
    }

    @Override
    public PaqueteEntity editarPaquete(Long id, PaqueteDto paqueteDto, HttpServletRequest request) {
        Optional<PaqueteEntity> findEntity = paqueteRepository.findById(id);
        if (!findEntity.isPresent()) {
            return null;
        }

        AuditoriaEntity auditoria = AuditoriaEntity.builder()
                .usuarioModificacion(jwtService.extractUsername(request.getHeader("Authorization").substring(7)))
                .fechaModificacion(new Date())
                .usuarioCreacion(findEntity.get().getAuditoriaEntity().getUsuarioCreacion())
                .fechaCreacion(new Date())
                .build();

        PaqueteEntity paqueteEntity = PaqueteEntity.builder()
                .paqueteId(findEntity.get().getPaqueteId())
                .nombre(paqueteDto.getNombre())
                .observacion(paqueteDto.getObservacion())
                .porcentaje(paqueteDto.getPorcentaje())
                .grupos(paqueteDto.getGrupos())
                .build();

        Set<GrupoEntity> grupos = new HashSet<>();
        for (GrupoEntity grupo : paqueteEntity.getGrupos()) {
            GrupoEntity existingGrupo = grupoRepository.findById(grupo.getGrupoId()).orElse(null);
            if (existingGrupo != null) {
                grupos.add(existingGrupo);
            } else {
                grupos.add(grupo);
            }
        }

        paqueteEntity.setGrupos(grupos);
        return paqueteRepository.save(paqueteEntity);

    }

    @Override
    public String eliminarPaquete(Long id) {
        PaqueteEntity findEntity = paqueteRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún usuario con id: " + id));

        paqueteRepository.deletePaqueteById(id);
        return "eliminado";
    }

    @Override
    public PaqueteEntity obtenerPaquetePorId(Long id) {

        return paqueteRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún paquete con id: " + id));

    }

    @Override
    public List<PaqueteEntity> obtenerPaquetes() {
        return paqueteRepository.findAll();
    }
}
