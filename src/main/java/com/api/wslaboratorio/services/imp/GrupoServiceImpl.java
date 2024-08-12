package com.api.wslaboratorio.services.imp;

import com.api.wslaboratorio.dto.GrupoDto;
import com.api.wslaboratorio.entities.AuditoriaEntity;
import com.api.wslaboratorio.entities.GrupoEntity;
import com.api.wslaboratorio.repositories.IGrupoRepository;
import com.api.wslaboratorio.services.IGrupoService;
import com.api.wslaboratorio.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GrupoServiceImpl implements IGrupoService {

    private final IGrupoRepository grupoRepository;
    private final JwtService jwtService;

    @Override
    public GrupoEntity crearGrupo(GrupoDto grupoDto, HttpServletRequest request) {
        AuditoriaEntity auditoria = AuditoriaEntity.builder()
                .usuarioCreacion(jwtService.extractUsername(request.getHeader("Authorization").substring(7)))
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
                .usuarioModificacion(jwtService.extractUsername(request.getHeader("Authorization").substring(7)))
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

       GrupoEntity findEntity = grupoRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún usuario con id: " + id));

        grupoRepository.deleteGrupoById(id);
        return "eliminado";
    }

    @Override
    public GrupoEntity obtenerGrupoPorId(Long id) {

        return grupoRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún grupo con id: " + id));
    }

    @Override
    public List<GrupoEntity> obtenerGrupos() {

        return grupoRepository.findAll();
    }
}
