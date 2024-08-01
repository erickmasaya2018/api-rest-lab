package com.api.wslaboratorio.services.imp;

import com.api.wslaboratorio.entities.AuditoriaEntity;
import com.api.wslaboratorio.dto.EmpresaDto;
import com.api.wslaboratorio.entities.EmpresaEntity;
import com.api.wslaboratorio.repositories.IEmpresaRepository;
import com.api.wslaboratorio.services.IEmpresaService;
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
public class EmpresaServiceImpl implements IEmpresaService {

    private final IEmpresaRepository empresaRepository;
    private final JwtService jwtService;

    @Override
    public EmpresaEntity crearEmpresa(EmpresaDto empresaDto, HttpServletRequest request) {

        AuditoriaEntity auditoria = AuditoriaEntity.builder()
                .usuarioCreacion(jwtService.extractUsername(request.getHeader("Authorization")))
                .fechaCreacion(new Date())
                .build();

        EmpresaEntity empresaEntity = EmpresaEntity.builder()
                .nombre(empresaDto.getNombre())
                .direccion(empresaDto.getDireccion())
                .ciudad(empresaDto.getCiudad())
                .estadoProvincia(empresaDto.getEstadoProvincia())
                .pais(empresaDto.getPais())
                .codigoPostal(empresaDto.getCodigoPostal())
                .email(empresaDto.getEmail())
                .sitioWeb(empresaDto.getSitioWeb())
                .auditoriaEntity(auditoria)
                .build();
        return empresaRepository.save(empresaEntity);
    }

    @Override
    public EmpresaEntity editarEmpresa(Long id, EmpresaDto empresaDto, HttpServletRequest request) {

        Optional<EmpresaEntity> findEntity = empresaRepository.findById(id);
        if (!findEntity.isPresent()) {
            return null;
        }

        AuditoriaEntity auditoria = AuditoriaEntity.builder()
                .usuarioModificacion(jwtService.extractUsername(request.getHeader("Authorization")))
                .fechaModificacion(new Date())
                .usuarioCreacion(findEntity.get().getAuditoriaEntity().getUsuarioCreacion())
                .fechaCreacion(new Date())
                .build();

        EmpresaEntity empresaEntity = EmpresaEntity.builder()
                .empresaId(findEntity.get().getEmpresaId())
                .nombre(empresaDto.getNombre())
                .direccion(empresaDto.getDireccion())
                .ciudad(empresaDto.getCiudad())
                .estadoProvincia(empresaDto.getEstadoProvincia())
                .pais(empresaDto.getPais())
                .codigoPostal(empresaDto.getCodigoPostal())
                .email(empresaDto.getEmail())
                .sitioWeb(empresaDto.getSitioWeb())
                .auditoriaEntity(auditoria)
                .build();

        return empresaRepository.save(empresaEntity);
    }

    @Override
    public String eliminarEmpresa(Long id) {
        Optional<EmpresaEntity> usuarioOptional = Optional.ofNullable(empresaRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún usuario con id: " + id)));

        if (!usuarioOptional.isPresent()) {
            return null;
        }

        empresaRepository.delete(usuarioOptional.get());
        return "eliminado";
    }

    @Override
    public Iterable<EmpresaEntity> obtenerEmpresaPorId(Long id) {
        Optional<EmpresaEntity> findEmpresa = Optional.ofNullable(empresaRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún usuario con id: " + id)));

        if (!findEmpresa.isPresent()) {
            return null;
        }

        return (Iterable<EmpresaEntity>) findEmpresa.get();
    }

    @Override
    public Page<EmpresaEntity> obtenerEmpresas(Pageable pageable) {
        return empresaRepository.findAll(pageable).map((element) -> element);
    }
}
