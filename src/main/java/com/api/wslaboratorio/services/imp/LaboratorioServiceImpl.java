package com.api.wslaboratorio.services.imp;

import com.api.wslaboratorio.dto.LaboratorioDto;
import com.api.wslaboratorio.entities.AuditoriaEntity;
import com.api.wslaboratorio.entities.LaboratorioEntity;
import com.api.wslaboratorio.repositories.ILaboratorioRepository;
import com.api.wslaboratorio.services.ILaboratorioService;
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
public class LaboratorioServiceImpl implements ILaboratorioService {

    private final ILaboratorioRepository laboratorioRepository;
    private final JwtService jwtService;

    @Override
    public LaboratorioEntity crearLaboratorio(LaboratorioDto laboratorioDto, HttpServletRequest request) {
        AuditoriaEntity auditoria = AuditoriaEntity.builder()
                .usuarioCreacion(jwtService.extractUsername(request.getHeader("Authorization")))
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
                .usuarioModificacion(jwtService.extractUsername(request.getHeader("Authorization")))
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

        Optional<LaboratorioEntity> findEntity = Optional.ofNullable(laboratorioRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún usuario con id: " + id)));

        if (!findEntity.isPresent()) {
            return null;
        }

        laboratorioRepository.delete(findEntity.get());

        return "eliminado";

    }

    @Override
    public Iterable<LaboratorioEntity> obtenerLaboratorioPorId(Long id) {

        Optional<LaboratorioEntity> findEmpresa = Optional.ofNullable(laboratorioRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún usuario con id: " + id)));

        if (!findEmpresa.isPresent()) {
            return null;
        }

        return (Iterable<LaboratorioEntity>) findEmpresa.get();
    }

    @Override
    public Page<LaboratorioEntity> obtenerLaboratorios(Pageable pageable) {
        return laboratorioRepository.findAll(pageable).map((element) -> element);
    }
}
