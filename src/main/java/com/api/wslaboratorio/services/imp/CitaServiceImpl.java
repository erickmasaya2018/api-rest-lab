package com.api.wslaboratorio.services.imp;

import com.api.wslaboratorio.dto.CitaDto;
import com.api.wslaboratorio.entities.*;
import com.api.wslaboratorio.repositories.ICitaRepository;
import com.api.wslaboratorio.repositories.IEstadoRepository;
import com.api.wslaboratorio.repositories.ILaboratorioRepository;
import com.api.wslaboratorio.repositories.IPacienteRepository;
import com.api.wslaboratorio.services.ICitaService;
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
public class CitaServiceImpl implements ICitaService {

    private final ICitaRepository citaRepository;
    private final IPacienteRepository pacienteRepository;
    private final ILaboratorioRepository laboratorioRepository;
    private final IEstadoRepository estadoRepository;
    private final JwtService jwtService;

    @Override
    public CitaEntity crearCita(CitaDto citaDto, HttpServletRequest request) {
        AuditoriaEntity auditoria = AuditoriaEntity.builder()
                .usuarioCreacion(jwtService.extractUsername(request.getHeader("Authorization")))
                .fechaCreacion(new Date())
                .build();

        PacienteEntity findPaciente = pacienteRepository.findById(citaDto.getPacienteEntity().getPacienteId())
                .orElseThrow(() -> new RuntimeException("No se encontró la entidad con el id: " + citaDto.getPacienteEntity().getPacienteId()));

        LaboratorioEntity findLaboratorio = laboratorioRepository.findById(citaDto.getLaboratorioEntity().getLaboratorioId())
                .orElseThrow(() -> new RuntimeException("No se encontró la entidad con el id: " + citaDto.getLaboratorioEntity().getLaboratorioId()));

        EstadoEntity findEstado = estadoRepository.findById(citaDto.getEstadoEntity().getEstadoId())
                .orElseThrow(() -> new RuntimeException("No se encontró la entidad con el id: " + citaDto.getEstadoEntity().getEstadoId()));

        CitaEntity citaEntity = CitaEntity.builder()
                .auditoriaEntity(auditoria)
                .pacienteEntity(findPaciente)
                .laboratorioEntity(findLaboratorio)
                .estadoEntity(findEstado)
                .build();

        return citaRepository.save(citaEntity);
    }

    @Override
    public CitaEntity editarCita(Long id, CitaDto citaDto, HttpServletRequest request) {

        Optional<CitaEntity> findEntity = citaRepository.findById(id);
        if (!findEntity.isPresent()) {
            return null;
        }

        AuditoriaEntity auditoria = AuditoriaEntity.builder()
                .usuarioModificacion(jwtService.extractUsername(request.getHeader("Authorization")))
                .fechaModificacion(new Date())
                .usuarioCreacion(findEntity.get().getAuditoriaEntity().getUsuarioCreacion())
                .fechaCreacion(new Date())
                .build();

        PacienteEntity findPaciente = pacienteRepository.findById(citaDto.getPacienteEntity().getPacienteId())
                .orElseThrow(() -> new RuntimeException("No se encontró la entidad con el id: " + citaDto.getPacienteEntity().getPacienteId()));

        LaboratorioEntity findLaboratorio = laboratorioRepository.findById(citaDto.getLaboratorioEntity().getLaboratorioId())
                .orElseThrow(() -> new RuntimeException("No se encontró la entidad con el id: " + citaDto.getLaboratorioEntity().getLaboratorioId()));

        EstadoEntity findEstado = estadoRepository.findById(citaDto.getEstadoEntity().getEstadoId())
                .orElseThrow(() -> new RuntimeException("No se encontró la entidad con el id: " + citaDto.getEstadoEntity().getEstadoId()));

        CitaEntity citaEntity = CitaEntity.builder()
                .auditoriaEntity(auditoria)
                .pacienteEntity(findPaciente)
                .laboratorioEntity(findLaboratorio)
                .estadoEntity(findEstado)
                .build();

        return citaRepository.save(citaEntity);
    }

    @Override
    public String eliminarCita(Long id) {
        Optional<CitaEntity> findEntity = Optional.ofNullable(citaRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún cita con id: " + id)));

        if (!findEntity.isPresent()) {
            return null;
        }

        citaRepository.delete(findEntity.get());
        return "eliminado";
    }

    @Override
    public Iterable<CitaEntity> obtenerCitaPorId(Long id) {
        Optional<CitaEntity> findEntity = Optional.ofNullable(citaRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún usuario con id: " + id)));

        if (!findEntity.isPresent()) {
            return null;
        }

        return (Iterable<CitaEntity>) findEntity.get();
    }

    @Override
    public Page<CitaEntity> obtenerCitas(Pageable pageable) {
        return citaRepository.findAll(pageable).map((element) -> element);
    }
}
