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
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
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
                .usuarioCreacion(jwtService.extractUsername(request.getHeader("Authorization").substring(7)))
                .fechaCreacion(new Date())
                .build();

        UnidadEntity findUnidadEntity = unidadRepository.findById(analisisDto.getUnidad().getUnidadId())
                .orElseThrow(() -> new RuntimeException("No se encontró a ninguna unidad con id: " + analisisDto.getUnidad().getUnidadId()));

        GrupoEntity findGrupoEntity = grupoRepository.findById(analisisDto.getGrupo().getGrupoId())
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún genero con id: " + analisisDto.getGrupo().getGrupoId()));

        AnalisisEntity analisisNuevo = AnalisisEntity.builder()
                .nombre(analisisDto.getNombre())
                .precio(analisisDto.getPrecio())
                .maximo(analisisDto.getMaximo())
                .minimo(analisisDto.getMinimo())
                .descripcion(analisisDto.getDescripcion())
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
                .usuarioModificacion(jwtService.extractUsername(request.getHeader("Authorization").substring(7)))
                .fechaModificacion(new Date())
                .usuarioCreacion(findEntity.get().getAuditoriaEntity().getUsuarioCreacion())
                .fechaCreacion(new Date())
                .build();

        UnidadEntity findUnidadEntity = unidadRepository.findById(analisisDto.getUnidad().getUnidadId())
                .orElseThrow(() -> new RuntimeException("No se encontró a ninguna unidad con id: " + analisisDto.getUnidad().getUnidadId()));

        GrupoEntity findGrupoEntity = grupoRepository.findById(analisisDto.getGrupo().getGrupoId())
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún grupo con id: " + analisisDto.getGrupo().getGrupoId()));

        AnalisisEntity analisisEntity = AnalisisEntity.builder()
                .nombre(analisisDto.getNombre())
                .precio(analisisDto.getPrecio())
                .maximo(analisisDto.getMaximo())
                .minimo(analisisDto.getMinimo())
                .descripcion(analisisDto.getDescripcion())
                .unidadEntity(findUnidadEntity)
                .grupoEntity(findGrupoEntity)
                .auditoriaEntity(auditoria)
                .build();

        return analisisRepository.save(analisisEntity);
    }

    @Override
    public String eliminarAnalisis(Long id) {
        analisisRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún análisis con id: " + id));

        analisisRepository.deleteAnalisisById(id);

        return "eliminado";

    }

    @Override
    public AnalisisEntity obtenerAnalisisPorId(Long id) {

        return analisisRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún análisis con id: " + id));
    }

    @Override
    public List<AnalisisEntity> obtenerAnalisis() {
        return analisisRepository.findAll();
    }
}
