package com.api.wslaboratorio.services.imp;

import com.api.wslaboratorio.dto.PacienteDto;
import com.api.wslaboratorio.dto.custom.PacienteCustomDto;
import com.api.wslaboratorio.entities.AuditoriaEntity;
import com.api.wslaboratorio.entities.PacienteEntity;
import com.api.wslaboratorio.repositories.IPacienteRepository;
import com.api.wslaboratorio.services.IPacienteService;
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
public class PacienteImpl implements IPacienteService {

    private final IPacienteRepository pacienteRepository;
    private final JwtService jwtService;

    @Override
    public PacienteEntity crearPaciente(PacienteDto pacienteDto, HttpServletRequest request) {
        AuditoriaEntity auditoria = AuditoriaEntity.builder()
                .usuarioCreacion(jwtService.extractUsername(request.getHeader("Authorization")))
                .fechaCreacion(new Date())
                .build();

        PacienteEntity pacienteEntity = PacienteEntity.builder()
                .dni(pacienteDto.getDni())
                .primerNombre(pacienteDto.getPrimerNombre())
                .segundoNombre(pacienteDto.getSegundoNombre())
                .tercerNombre(pacienteDto.getTercerNombre())
                .primerApellido(pacienteDto.getPrimerApellido())
                .segundoApellido(pacienteDto.getSegundoApellido())
                .direccion(pacienteDto.getDireccion())
                .ciudad(pacienteDto.getCiudad())
                .telefono(pacienteDto.getTelefono())
                .generoEntity(pacienteDto.getGeneroEntity())
                .auditoriaEntity(auditoria)
                .build();

        return pacienteRepository.save(pacienteEntity);
    }

    @Override
    public PacienteEntity editarPaciente(Long id, PacienteDto pacienteDto, HttpServletRequest request) {
        Optional<PacienteEntity> findEntity = pacienteRepository.findById(id);
        if (!findEntity.isPresent()) {
            return null;
        }

        AuditoriaEntity auditoria = AuditoriaEntity.builder()
                .usuarioModificacion(jwtService.extractUsername(request.getHeader("Authorization")))
                .fechaModificacion(new Date())
                .usuarioCreacion(findEntity.get().getAuditoriaEntity().getUsuarioCreacion())
                .fechaCreacion(new Date())
                .build();

        PacienteEntity pacienteEntity = PacienteEntity.builder()
                .dni(pacienteDto.getDni())
                .primerNombre(pacienteDto.getPrimerNombre())
                .segundoNombre(pacienteDto.getSegundoNombre())
                .tercerNombre(pacienteDto.getTercerNombre())
                .primerApellido(pacienteDto.getPrimerApellido())
                .segundoApellido(pacienteDto.getSegundoApellido())
                .direccion(pacienteDto.getDireccion())
                .ciudad(pacienteDto.getCiudad())
                .telefono(pacienteDto.getTelefono())
                .generoEntity(pacienteDto.getGeneroEntity())
                .auditoriaEntity(auditoria)
                .build();

        return pacienteRepository.save(pacienteEntity);
    }

    @Override
    public String eliminarPaciente(Long id) {

        Optional<PacienteEntity> findEntity = Optional.ofNullable(pacienteRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún usuario con id: " + id)));

        if (!findEntity.isPresent()) {
            return null;
        }

        pacienteRepository.delete(findEntity.get());
        return "eliminado";
    }

    @Override
    public Iterable<PacienteEntity> obtenerPacientePorId(Long id) {
        Optional<PacienteEntity> findEntity = Optional.ofNullable(pacienteRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún usuario con id: " + id)));

        if (!findEntity.isPresent()) {
            return null;
        }

        return (Iterable<PacienteEntity>) findEntity.get();
    }

    @Override
    public Page<PacienteEntity> obtenerPacientes(Pageable pageable) {
        return pacienteRepository.findAll(pageable).map((element) -> element);
    }

    @Override
    public PacienteCustomDto obtenerAnalisisPorPacienteId(Long pacienteId) {
        return pacienteRepository.ObtenerAnalisisPorIdPaciente(pacienteId);
    }
}
