package com.api.wslaboratorio.services.imp;

import com.api.wslaboratorio.dto.AnalisisDto;
import com.api.wslaboratorio.entities.*;
import com.api.wslaboratorio.repositories.IAnalisisRepository;
import com.api.wslaboratorio.repositories.IGrupoRepository;
import com.api.wslaboratorio.repositories.IUnidadRepository;
import com.api.wslaboratorio.services.IAnalisisService;
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
public class AnalisisServiceImpl implements IAnalisisService {

    private final IAnalisisRepository analisisRepository;
    private final IGrupoRepository grupoRepository;
    private final IUnidadRepository unidadRepository;
    private final JwtService jwtService;

    @Override
    public AnalisisEntity crearAnalisis(AnalisisDto analisisDto, HttpServletRequest request) {
        AuditoriaEntity auditoria = AuditoriaEntity.builder()
                .usuarioCreacion(jwtService.extractUsername(request.getHeader("Authorization")))
                .fechaCreacion(new Date())
                .build();

        UnidadEntity findUnidadEntity = unidadRepository.findById(analisisDto.getUnidadEntity().getUnidadId())
                .orElseThrow(() -> new RuntimeException("No se encontró a ninguna empresa con id: " + analisisDto.getUnidadEntity().getUnidadId()));

        GrupoEntity findGrupoEntity = grupoRepository.findById(analisisDto.getGrupoEntity().getGrupoId())
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún genero con id: " + analisisDto.getGrupoEntity().getGrupoId()));

        AnalisisEntity analisisNuevo = AnalisisEntity.builder()
                .nombre(analisisDto.getNombre())
                .precio(analisisDto.getPrecio())
                .maximo(analisisDto.getMaximo())
                .minimo(analisisDto.getMinimo())
                .unidadEntity(findUnidadEntity)
                .grupoEntity(findGrupoEntity)
                .auditoriaEntity(auditoria)
                .build();
        return analisisRepository.save(analisisNuevo);
    }

    @Override
    public AnalisisEntity editarAnalisis(Long id, AnalisisDto analisisDto, HttpServletRequest request) {
        Optional<AnalisisEntity> findEntity = analisisRepository.findById(id);
        if (!findEntity.isPresent()) {
            return null;
        }

        AuditoriaEntity auditoria = AuditoriaEntity.builder()
                .usuarioModificacion(jwtService.extractUsername(request.getHeader("Authorization")))
                .fechaModificacion(new Date())
                .usuarioCreacion(findEntity.get().getAuditoriaEntity().getUsuarioCreacion())
                .fechaCreacion(new Date())
                .build();

        UnidadEntity findUnidadEntity = unidadRepository.findById(analisisDto.getUnidadEntity().getUnidadId())
                .orElseThrow(() -> new RuntimeException("No se encontró a ninguna unidad con id: " + analisisDto.getUnidadEntity().getUnidadId()));

        GrupoEntity findGrupoEntity = grupoRepository.findById(analisisDto.getGrupoEntity().getGrupoId())
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún grupo con id: " + analisisDto.getGrupoEntity().getGrupoId()));

        AnalisisEntity analisisEntity = AnalisisEntity.builder()
                .nombre(analisisDto.getNombre())
                .precio(analisisDto.getPrecio())
                .maximo(analisisDto.getMaximo())
                .minimo(analisisDto.getMinimo())
                .unidadEntity(findUnidadEntity)
                .grupoEntity(findGrupoEntity)
                .auditoriaEntity(auditoria)
                .build();

        return analisisRepository.save(analisisEntity);
    }


    @Override
    public String eliminarAnalisis(Long id) {
        Optional<AnalisisEntity> findEntity = Optional.ofNullable(analisisRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún usuario con id: " + id)));

        if (!findEntity.isPresent()) {
            return null;
        }

        analisisRepository.delete(findEntity.get());
        return "eliminado";

    }

    @Override
    public Iterable<AnalisisEntity> obtenerAnalisisPorId(Long id) {
        Optional<AnalisisEntity> findEntity = Optional.ofNullable(analisisRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún usuario con id: " + id)));

        if (!findEntity.isPresent()) {
            return null;
        }

        return (Iterable<AnalisisEntity>) findEntity.get();
    }

    @Override
    public Page<AnalisisEntity> obtenerAnalisis(Pageable pageable) {
        return analisisRepository.findAll(pageable).map((element) -> element);
    }
}
