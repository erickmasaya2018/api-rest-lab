package com.api.wslaboratorio.services.imp;

import com.api.wslaboratorio.dto.LaboratorioDto;
import com.api.wslaboratorio.entities.AuditoriaEntity;
import com.api.wslaboratorio.entities.LaboratorioEntity;
import com.api.wslaboratorio.repositories.ILaboratorioRepository;
import com.api.wslaboratorio.services.ILaboratorioService;
import com.api.wslaboratorio.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LaboratorioServiceImpl implements ILaboratorioService {

    private final ILaboratorioRepository laboratorioRepository;
    private final JwtService jwtService;

    @Override
    public LaboratorioEntity crearLaboratorio(LaboratorioDto laboratorioDto, HttpServletRequest request) {
        AuditoriaEntity auditoria = AuditoriaEntity.builder()
                .usuarioCreacion(jwtService.extractUsername(request.getHeader("Authorization").substring(7)))
                .fechaCreacion(new Date())
                .build();

        LaboratorioEntity laboratorioEntity = LaboratorioEntity.builder()
                .nombre(laboratorioDto.getNombre())
                .direccion(laboratorioDto.getDireccion())
                .telefono(laboratorioDto.getTelefono())
                .email(laboratorioDto.getEmail())
                .auditoriaEntity(auditoria)
                .empresaEntity(laboratorioDto.getEmpresaEntity())
                .build();

        return laboratorioRepository.save(laboratorioEntity);
    }

    @Override
    public LaboratorioEntity editarLaboratorio(Long id, LaboratorioDto laboratorioDto, HttpServletRequest request) {

        Optional<LaboratorioEntity> findEntity = laboratorioRepository.findById(id);
        if (!findEntity.isPresent()) {
            return null;
        }

        AuditoriaEntity auditoria = AuditoriaEntity.builder()
                .usuarioModificacion(jwtService.extractUsername(request.getHeader("Authorization").substring(7)))
                .fechaModificacion(new Date())
                .usuarioCreacion(findEntity.get().getAuditoriaEntity().getUsuarioCreacion())
                .fechaCreacion(new Date())
                .build();

        LaboratorioEntity laboratorioEntity = LaboratorioEntity.builder()
                .laboratorioId(findEntity.get().getLaboratorioId())
                .nombre(laboratorioDto.getNombre())
                .direccion(laboratorioDto.getDireccion())
                .telefono(laboratorioDto.getTelefono())
                .email(laboratorioDto.getEmail())
                .auditoriaEntity(auditoria)
                .empresaEntity(laboratorioDto.getEmpresaEntity())
                .build();

        return laboratorioRepository.save(laboratorioEntity);
    }

    @Override
    public String eliminarLaboratorio(Long id) {

      LaboratorioEntity findEntity =laboratorioRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún usuario con id: " + id));

        laboratorioRepository.deleteLaboratorioById(id);
        return "eliminado";

    }

    @Override
    public LaboratorioEntity obtenerLaboratorioPorId(Long id) {

        return laboratorioRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún laboratorio con id: " + id));
    }

    @Override
    public List<LaboratorioEntity> obtenerLaboratorios() {
        return laboratorioRepository.findAll();
    }
}
