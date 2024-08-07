package com.api.wslaboratorio.services.imp;

import com.api.wslaboratorio.dto.EmpleadoDto;
import com.api.wslaboratorio.entities.AuditoriaEntity;
import com.api.wslaboratorio.entities.EmpleadoEntity;
import com.api.wslaboratorio.entities.EmpresaEntity;
import com.api.wslaboratorio.entities.GeneroEntity;
import com.api.wslaboratorio.repositories.IEmpleadoRepository;
import com.api.wslaboratorio.repositories.IEmpresaRepository;
import com.api.wslaboratorio.repositories.IGeneroRepository;
import com.api.wslaboratorio.services.IEmpleadoService;
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
public class EmpleadoServiceImpl implements IEmpleadoService {
    private final IEmpleadoRepository empleadoRepository;
    private final IEmpresaRepository empresaRepository;
    private final IGeneroRepository generoRepository;
    private final JwtService jwtService;

    @Override
    public EmpleadoEntity crearEmpleado(EmpleadoDto empleadoDto, HttpServletRequest request) {
        AuditoriaEntity auditoria = AuditoriaEntity.builder()
                .usuarioCreacion(jwtService.extractUsername(request.getHeader("Authorization")))
                .fechaCreacion(new Date())
                .build();

        EmpresaEntity findEmpresaEntity = empresaRepository.findById(empleadoDto.getEmpresaEntity().getEmpresaId())
                .orElseThrow(() -> new RuntimeException("No se encontró a ninguna empresa con id: " + empleadoDto.getEmpresaEntity().getEmpresaId()));

        GeneroEntity findGeneroEntity = generoRepository.findById(empleadoDto.getGeneroEntity().getGeneroId())
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún genero con id: " + empleadoDto.getGeneroEntity().getGeneroId()));

        EmpleadoEntity empleadoNuevo = EmpleadoEntity.builder()
                .dni(empleadoDto.getDni())
                .primerNombre(empleadoDto.getPrimerNombre())
                .segundoNombre(empleadoDto.getSegundoNombre())
                .tercerNombre(empleadoDto.getTercerNombre())
                .primerApellido(empleadoDto.getPrimerApellido())
                .segundoApellido(empleadoDto.getSegundoApellido())
                .direccion(empleadoDto.getDireccion())
                .ciudad(empleadoDto.getCiudad())
                .estadoprovincia(empleadoDto.getEstadoprovincia())
                .fechaNacimiento(empleadoDto.getFechaNacimiento())
                .telefono(empleadoDto.getTelefono())
                .empresaEntity(findEmpresaEntity)
                .generoEntity(findGeneroEntity)
                .auditoriaEntity(auditoria)
                .build();

        return empleadoRepository.save(empleadoNuevo);
    }

    @Override
    public EmpleadoEntity editarEmpleado(Long id, EmpleadoDto empleadoDto, HttpServletRequest request) {

        Optional<EmpleadoEntity> findEntity = empleadoRepository.findById(id);
        if (!findEntity.isPresent()) {
            return null;
        }

        AuditoriaEntity auditoria = AuditoriaEntity.builder()
                .usuarioModificacion(jwtService.extractUsername(request.getHeader("Authorization")))
                .fechaModificacion(new Date())
                .usuarioCreacion(findEntity.get().getAuditoriaEntity().getUsuarioCreacion())
                .fechaCreacion(new Date())
                .build();

        EmpresaEntity findEmpresaEntity = empresaRepository.findById(empleadoDto.getEmpresaEntity().getEmpresaId())
                .orElseThrow(() -> new RuntimeException("No se encontró a ninguna empresa con id: " + empleadoDto.getEmpresaEntity().getEmpresaId()));

        GeneroEntity findGeneroEntity = generoRepository.findById(empleadoDto.getGeneroEntity().getGeneroId())
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún genero con id: " + empleadoDto.getGeneroEntity().getGeneroId()));

        EmpleadoEntity empleadoEntity = EmpleadoEntity.builder()
                .dni(empleadoDto.getDni())
                .primerNombre(empleadoDto.getPrimerNombre())
                .segundoNombre(empleadoDto.getSegundoNombre())
                .tercerNombre(empleadoDto.getTercerNombre())
                .primerApellido(empleadoDto.getPrimerApellido())
                .segundoApellido(empleadoDto.getSegundoApellido())
                .direccion(empleadoDto.getDireccion())
                .ciudad(empleadoDto.getCiudad())
                .estadoprovincia(empleadoDto.getEstadoprovincia())
                .fechaNacimiento(empleadoDto.getFechaNacimiento())
                .telefono(empleadoDto.getTelefono())
                .empresaEntity(findEmpresaEntity)
                .generoEntity(findGeneroEntity)
                .auditoriaEntity(auditoria)
                .build();

        return empleadoRepository.save(empleadoEntity);
    }

    @Override
    public String eliminarEmpleado(Long id) {
        Optional<EmpleadoEntity> findEntity = Optional.ofNullable(empleadoRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún usuario con id: " + id)));

        if (!findEntity.isPresent()) {
            return null;
        }

        empleadoRepository.delete(findEntity.get());
        return "eliminado";
    }

    @Override
    public Iterable<EmpleadoEntity> obtenerEmpleadoPorId(Long id) {
        Optional<EmpleadoEntity> findEntity = Optional.ofNullable(empleadoRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún usuario con id: " + id)));

        if (!findEntity.isPresent()) {
            return null;
        }

        return (Iterable<EmpleadoEntity>) findEntity.get();
    }

    @Override
    public Page<EmpleadoEntity> obtenerEmpleados(Pageable pageable) {
        return empleadoRepository.findAll(pageable).map((element) -> element);
    }
}
