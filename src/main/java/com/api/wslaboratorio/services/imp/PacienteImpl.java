package com.api.wslaboratorio.services.imp;

import com.api.wslaboratorio.dto.PacienteDto;
import com.api.wslaboratorio.dto.custom.PacienteCustomDto;
import com.api.wslaboratorio.entities.AuditoriaEntity;
import com.api.wslaboratorio.entities.GeneroEntity;
import com.api.wslaboratorio.entities.PacienteEntity;
import com.api.wslaboratorio.repositories.IGeneroRepository;
import com.api.wslaboratorio.repositories.IPacienteRepository;
import com.api.wslaboratorio.services.IPacienteService;
import com.api.wslaboratorio.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PacienteImpl implements IPacienteService {

    private final IPacienteRepository pacienteRepository;
    private final IGeneroRepository generoRepository;
    private final JwtService jwtService;

    @Override
    public PacienteEntity crearPaciente(PacienteDto pacienteDto, HttpServletRequest request) {
        AuditoriaEntity auditoria = AuditoriaEntity.builder()
                .usuarioCreacion(jwtService.extractUsername(request.getHeader("Authorization").substring(7)))
                .fechaCreacion(new Date())
                .build();

        GeneroEntity findEntity = generoRepository.findById(pacienteDto.getGeneroId())
                .orElseThrow(() -> new RuntimeException("No se encontró la entidad con el id: " + pacienteDto.getGeneroId()));

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
                .generoEntity(findEntity)
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
                .usuarioModificacion(jwtService.extractUsername(request.getHeader("Authorization").substring(7)))
                .fechaModificacion(new Date())
                .usuarioCreacion(findEntity.get().getAuditoriaEntity().getUsuarioCreacion())
                .fechaCreacion(new Date())
                .build();

        GeneroEntity generoEntity = generoRepository.findById(pacienteDto.getGeneroId())
                .orElseThrow(() -> new RuntimeException("No se encontró la entidad con el id: " + pacienteDto.getGeneroId()));


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
                .generoEntity(generoEntity)
                .auditoriaEntity(auditoria)
                .build();

        return pacienteRepository.save(pacienteEntity);
    }

    @Override
    public String eliminarPaciente(Long id) {

        PacienteEntity findEntity = pacienteRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún usuario con id: " + id));

        pacienteRepository.deletePacienteById(id);
        return "eliminado";
    }

    @Override
    public PacienteEntity obtenerPacientePorId(Long id) {

        return pacienteRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún paciente con id: " + id));
    }

    @Override
    public List<PacienteEntity> obtenerPacientes() {
        return pacienteRepository.findAll();
    }

    @Override
    public PacienteCustomDto obtenerAnalisisPorPacienteId(Long pacienteId) {
        return pacienteRepository.ObtenerAnalisisPorIdPaciente(pacienteId);
    }
}
