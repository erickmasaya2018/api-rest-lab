package com.api.wslaboratorio.services.imp;

import com.api.wslaboratorio.dto.EmpresaDto;
import com.api.wslaboratorio.entities.AuditoriaEntity;
import com.api.wslaboratorio.entities.EmpresaEntity;
import com.api.wslaboratorio.repositories.IEmpresaRepository;
import com.api.wslaboratorio.services.IEmpresaService;
import com.api.wslaboratorio.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmpresaServiceImpl implements IEmpresaService {

    private final IEmpresaRepository empresaRepository;
    private final JwtService jwtService;

    @Override
    public EmpresaEntity crearEmpresa(EmpresaDto empresaDto, HttpServletRequest request) {

        AuditoriaEntity auditoria = AuditoriaEntity.builder()
                .usuarioCreacion(jwtService.extractUsername(request.getHeader("Authorization").substring(7)))
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
                .telefono(empresaDto.getTelefono())
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
                .usuarioModificacion(jwtService.extractUsername(request.getHeader("Authorization").substring(7)))
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
                .telefono(empresaDto.getTelefono())
                .auditoriaEntity(auditoria)
                .build();

        return empresaRepository.save(empresaEntity);
    }

    @Override
    public String eliminarEmpresa(Long id) {
        EmpresaEntity findEntity = empresaRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún usuario con id: " + id));

        empresaRepository.deleteEmpresaById(id);
        return "eliminado";
    }

    @Override
    public EmpresaEntity obtenerEmpresaPorId(Long id) {

        return empresaRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún empresa con id: " + id));
    }

    @Override
    public List<EmpresaEntity> obtenerEmpresas() {
        return empresaRepository.findAll();
    }
}
