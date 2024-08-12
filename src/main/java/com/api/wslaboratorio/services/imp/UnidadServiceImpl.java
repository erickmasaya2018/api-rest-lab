package com.api.wslaboratorio.services.imp;

import com.api.wslaboratorio.dto.UnidadDto;
import com.api.wslaboratorio.entities.AuditoriaEntity;
import com.api.wslaboratorio.entities.UnidadEntity;
import com.api.wslaboratorio.repositories.IUnidadRepository;
import com.api.wslaboratorio.services.IUnidadService;
import com.api.wslaboratorio.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UnidadServiceImpl implements IUnidadService {
    private final IUnidadRepository unidadRepository;
    private final JwtService jwtService;

    @Override
    public UnidadEntity crearUnidad(UnidadDto unidadDto, HttpServletRequest request) {

        AuditoriaEntity auditoria = AuditoriaEntity.builder()
                .usuarioCreacion(jwtService.extractUsername(request.getHeader("Authorization").substring(7)))
                .fechaCreacion(new Date())
                .build();

        UnidadEntity unidadEntity = UnidadEntity.builder()
                .nombre(unidadDto.getNombre())
                .observacion(unidadDto.getObservacion())
                .auditoriaEntity(auditoria)
                .build();

        return unidadRepository.save(unidadEntity);
    }

    @Override
    public UnidadEntity editarUnidad(Long id, UnidadDto unidadDto, HttpServletRequest request) {
        Optional<UnidadEntity> findEntity = unidadRepository.findById(id);

        if (!findEntity.isPresent()) {
            return null;
        }

        AuditoriaEntity auditoria = AuditoriaEntity.builder()
                .usuarioModificacion(jwtService.extractUsername(request.getHeader("Authorization").substring(7)))
                .fechaModificacion(new Date())
                .usuarioCreacion(findEntity.get().getAuditoriaEntity().getUsuarioCreacion())
                .fechaCreacion(new Date())
                .build();

        UnidadEntity unidadEntity = UnidadEntity.builder()
                .unidadId(findEntity.get().getUnidadId())
                .nombre(unidadDto.getNombre())
                .observacion(unidadDto.getObservacion())
                .auditoriaEntity(auditoria)
                .build();

        return unidadRepository.save(unidadEntity);
    }

    @Override
    public String eliminarUnidad(Long id) {

        Optional<UnidadEntity> findEntity = Optional.ofNullable(unidadRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún usuario con id: " + id)));

        unidadRepository.deleteUnidadById(id);
        return "eliminado";
    }

    @Override
    public UnidadEntity obtenerUnidadPorId(Long id) {

        return unidadRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún unidad con id: " + id));
    }

    @Override
    public List<UnidadEntity> obtenerUnidades() {
        return unidadRepository.findAll();

    }
}
