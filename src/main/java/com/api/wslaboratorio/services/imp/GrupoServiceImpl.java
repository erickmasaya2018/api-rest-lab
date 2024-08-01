package com.api.wslaboratorio.services.imp;

import com.api.wslaboratorio.dto.GrupoDto;
import com.api.wslaboratorio.entities.AuditoriaEntity;
import com.api.wslaboratorio.entities.GrupoEntity;
import com.api.wslaboratorio.repositories.IGrupoRepository;
import com.api.wslaboratorio.services.IGrupoService;
import com.api.wslaboratorio.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GrupoServiceImpl implements IGrupoService {

    private final IGrupoRepository grupoRepository;
    private final JwtService jwtService;

    @Override
    public GrupoEntity crearGrupo(GrupoDto grupoDto, HttpServletRequest request) {
        AuditoriaEntity auditoria = AuditoriaEntity.builder()
                .usuarioCreacion(jwtService.extractUsername(request.getHeader("Authorization")))
                .fechaCreacion(new Date())
                .build();

        GrupoEntity grupoEntity = GrupoEntity.builder()
                .nombre(grupoDto.getNombre())
                .observacion(grupoDto.getObservacion())
                .auditoriaEntity(auditoria)
                .build();
        return grupoRepository.save(grupoEntity);
    }

    @Override
    public GrupoEntity editarGrupo(Long id, GrupoDto grupoDto, HttpServletRequest request) {
        Optional<GrupoEntity> findEntity = grupoRepository.findById(id);
        if (!findEntity.isPresent()) {
            return null;
        }

        AuditoriaEntity auditoria = AuditoriaEntity.builder()
                .usuarioModificacion(jwtService.extractUsername(request.getHeader("Authorization")))
                .fechaModificacion(new Date())
                .usuarioCreacion(findEntity.get().getAuditoriaEntity().getUsuarioCreacion())
                .fechaCreacion(new Date())
                .build();

        GrupoEntity grupoEntity = GrupoEntity.builder()
                .grupoId(findEntity.get().getGrupoId())
                .nombre(grupoDto.getNombre())
                .observacion(grupoDto.getObservacion())
                .auditoriaEntity(auditoria)
                .build();

        return grupoRepository.save(grupoEntity);
    }

    @Override
    public String eliminarGrupo(Long id) {

        Optional<GrupoEntity> usuarioOptional = Optional.ofNullable(grupoRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún usuario con id: " + id)));

        if (!usuarioOptional.isPresent()) {
            return null;
        }

        grupoRepository.delete(usuarioOptional.get());
        return "eliminado";
    }

    @Override
    public Iterable<GrupoEntity> obtenerGrupoPorId(Long id) {

        Optional<GrupoEntity> grupo = Optional.ofNullable(grupoRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún usuario con id: " + id)));

        if (!grupo.isPresent()) {
            return null;
        }

        return (Iterable<GrupoEntity>) grupo.get();
    }

    @Override
    public Page<GrupoEntity> obtenerGrupos(Pageable pageable) {

        return grupoRepository.findAll(pageable).map((element) -> element);
    }
}
