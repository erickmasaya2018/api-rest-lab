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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
                .usuarioCreacion(jwtService.extractUsername(request.getHeader("Authorization")))
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
                .usuarioModificacion(jwtService.extractUsername(request.getHeader("Authorization")))
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
        Optional<PaqueteEntity> findEntity = Optional.ofNullable(paqueteRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún usuario con id: " + id)));

        if (!findEntity.isPresent()) {
            return null;
        }

        paqueteRepository.delete(findEntity.get());
        return "eliminado";
    }

    @Override
    public Iterable<PaqueteEntity> obtenerPaquetePorId(Long id) {
        Optional<PaqueteEntity> findEntity = Optional.ofNullable(paqueteRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún usuario con id: " + id)));

        if (!findEntity.isPresent()) {
            return null;
        }

        return (Iterable<PaqueteEntity>) findEntity.get();
    }

    @Override
    public Page<PaqueteEntity> obtenerPaquetes(Pageable pageable) {
        return paqueteRepository.findAll(pageable).map((element) -> element);
    }
}
