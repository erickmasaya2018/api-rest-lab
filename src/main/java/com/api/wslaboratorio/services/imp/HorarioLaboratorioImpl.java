package com.api.wslaboratorio.services.imp;

import com.api.wslaboratorio.dto.HorarioLaborarioEntityDto;
import com.api.wslaboratorio.entities.AuditoriaEntity;
import com.api.wslaboratorio.entities.HorarioLaborarioEntity;
import com.api.wslaboratorio.repositories.IHorarioLaboratorioRepository;
import com.api.wslaboratorio.services.IHorarioLaboratorioService;
import com.api.wslaboratorio.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HorarioLaboratorioImpl implements IHorarioLaboratorioService {

    private final IHorarioLaboratorioRepository horarioLaboratorioRepository;
    private final JwtService jwtService;

    @Override
    public HorarioLaborarioEntity crearHorarioLaboratorio(HorarioLaborarioEntityDto horarioLaborarioEntityDto, HttpServletRequest request) {
        AuditoriaEntity auditoria = AuditoriaEntity.builder()
                .usuarioCreacion(jwtService.extractUsername(request.getHeader("Authorization").substring(7)))
                .fechaCreacion(new Date())
                .build();

        HorarioLaborarioEntity horarioLaborarioEntity = HorarioLaborarioEntity.builder()
                .diaSemana(horarioLaborarioEntityDto.getDiaSemana())
                .abierto(horarioLaborarioEntityDto.getAbierto())
                .horaAbre(horarioLaborarioEntityDto.getHoraAbre())
                .horaCierra(horarioLaborarioEntityDto.getHoraCierra())
                .auditoriaEntity(auditoria)
                .laboratorioEntity(horarioLaborarioEntityDto.getLaboratorioEntity())
                .build();

        return horarioLaboratorioRepository.save(horarioLaborarioEntity);
    }

    @Override
    public HorarioLaborarioEntity editarHorarioLaboratorio(Long id, HorarioLaborarioEntityDto horarioLaborarioEntityDto, HttpServletRequest request) {
        Optional<HorarioLaborarioEntity> findEntity = horarioLaboratorioRepository.findById(id);
        if (!findEntity.isPresent()) {
            return null;
        }

        AuditoriaEntity auditoria = AuditoriaEntity.builder()
                .usuarioModificacion(jwtService.extractUsername(request.getHeader("Authorization").substring(7)))
                .fechaModificacion(new Date())
                .usuarioCreacion(findEntity.get().getAuditoriaEntity().getUsuarioCreacion())
                .fechaCreacion(new Date())
                .build();

        HorarioLaborarioEntity horarioLaborarioEntity = HorarioLaborarioEntity.builder()
                .horarioLaboratorioId(findEntity.get().getHorarioLaboratorioId())
                .diaSemana(horarioLaborarioEntityDto.getDiaSemana())
                .abierto(horarioLaborarioEntityDto.getAbierto())
                .horaAbre(horarioLaborarioEntityDto.getHoraAbre())
                .horaCierra(horarioLaborarioEntityDto.getHoraCierra())
                .auditoriaEntity(auditoria)
                .laboratorioEntity(horarioLaborarioEntityDto.getLaboratorioEntity())
                .build();

        return horarioLaboratorioRepository.save(horarioLaborarioEntity);
    }

    @Override
    public String eliminarHorarioLaboratorio(Long id) {
        HorarioLaborarioEntity findEntity = horarioLaboratorioRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún usuario con id: " + id));

        horarioLaboratorioRepository.deleteHorarioLaboratorioById(id);
        return "eliminado";
    }

    @Override
    public HorarioLaborarioEntity obtenerHorarioLaboratorioPorId(Long id) {

        return horarioLaboratorioRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún horario laboral con id: " + id));
    }

    @Override
    public List<HorarioLaborarioEntity> obtenerHorarioLaboratorios() {
        return horarioLaboratorioRepository.findAll();
    }
}
