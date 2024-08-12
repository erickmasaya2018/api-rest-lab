package com.api.wslaboratorio.services.imp;

import com.api.wslaboratorio.dto.EstadoDto;
import com.api.wslaboratorio.entities.AuditoriaEntity;
import com.api.wslaboratorio.entities.EstadoEntity;
import com.api.wslaboratorio.repositories.IEstadoRepository;
import com.api.wslaboratorio.services.IEstadoService;
import com.api.wslaboratorio.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EstadoServiceImpl implements IEstadoService {
    private final IEstadoRepository estadoRepository;
    private final JwtService jwtService;

    @Override
    public EstadoEntity crearEstado(EstadoDto estadoDto, HttpServletRequest request) {

        AuditoriaEntity auditoria = AuditoriaEntity.builder()
                .usuarioCreacion(jwtService.extractUsername(request.getHeader("Authorization").substring(7)))
                .fechaCreacion(new Date())
                .build();

        EstadoEntity estadoEntity = EstadoEntity.builder()
                .descripcion(estadoDto.getDescripcion())
                .auditoriaEntity(auditoria)
                .build();

        return estadoRepository.save(estadoEntity);
    }

    @Override
    public EstadoEntity editarEstado(Long id, EstadoDto estadoDto, HttpServletRequest request) {

        Optional<EstadoEntity> findEntity = estadoRepository.findById(id);
        if (!findEntity.isPresent()) {
            return null;
        }

        AuditoriaEntity auditoria = AuditoriaEntity.builder()
                .usuarioModificacion(jwtService.extractUsername(request.getHeader("Authorization").substring(7)))
                .fechaModificacion(new Date())
                .usuarioCreacion(findEntity.get().getAuditoriaEntity().getUsuarioCreacion())
                .fechaCreacion(new Date())
                .build();

        EstadoEntity estadoEntity = EstadoEntity.builder()
                .estadoId(findEntity.get().getEstadoId())
                .descripcion(estadoDto.getDescripcion())
                .auditoriaEntity(auditoria)
                .build();

        return estadoRepository.save(estadoEntity);

    }

    @Override
    public String eliminarEstado(Long id) {

        EstadoEntity findEntity = estadoRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún usuario con id: " + id));

        estadoRepository.deleteEstadoById(id);
        return "eliminado";

    }

    @Override
    public EstadoEntity obtenerEstadoPorId(Long id) {

        return estadoRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún estado con id: " + id));
    }

    @Override
    public List<EstadoEntity> obtenerEstados() {
        return estadoRepository.findAll();
    }
}
