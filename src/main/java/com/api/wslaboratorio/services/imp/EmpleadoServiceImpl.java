package com.api.wslaboratorio.services.imp;

import com.api.wslaboratorio.dto.EmpleadoDto;
import com.api.wslaboratorio.entities.AuditoriaEntity;
import com.api.wslaboratorio.entities.EmpleadoEntity;
import com.api.wslaboratorio.entities.GeneroEntity;
import com.api.wslaboratorio.entities.LaboratorioEntity;
import com.api.wslaboratorio.repositories.IEmpleadoRepository;
import com.api.wslaboratorio.repositories.IGeneroRepository;
import com.api.wslaboratorio.repositories.ILaboratorioRepository;
import com.api.wslaboratorio.services.IEmpleadoService;
import com.api.wslaboratorio.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmpleadoServiceImpl implements IEmpleadoService {
    private final IEmpleadoRepository empleadoRepository;
    private final ILaboratorioRepository laboratorioRepository;
    private final IGeneroRepository generoRepository;
    private final JwtService jwtService;

    @Override
    public EmpleadoEntity crearEmpleado(EmpleadoDto empleadoDto, HttpServletRequest request) {
        AuditoriaEntity auditoria = AuditoriaEntity.builder()
                .usuarioCreacion(jwtService.extractUsername(request.getHeader("Authorization").substring(7)))
                .fechaCreacion(new Date())
                .build();

        LaboratorioEntity findLaboratorioEntity = laboratorioRepository.findById(empleadoDto.getLaboratorioId())
                .orElseThrow(() -> new RuntimeException("No se encontró a ninguna empresa con id: " + empleadoDto.getLaboratorioId()));

        GeneroEntity findGeneroEntity = generoRepository.findById(empleadoDto.getGeneroId())
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún genero con id: " + empleadoDto.getGeneroId()));

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
                .laboratorioEntity(findLaboratorioEntity)
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
                .usuarioModificacion(jwtService.extractUsername(request.getHeader("Authorization").substring(7)))
                .fechaModificacion(new Date())
                .usuarioCreacion(findEntity.get().getAuditoriaEntity().getUsuarioCreacion())
                .fechaCreacion(new Date())
                .build();

        LaboratorioEntity findLaboratorioEntity = laboratorioRepository.findById(empleadoDto.getLaboratorioId())
                .orElseThrow(() -> new RuntimeException("No se encontró a ninguna empresa con id: " + empleadoDto.getLaboratorioId()));

        GeneroEntity findGeneroEntity = generoRepository.findById(empleadoDto.getGeneroId())
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún genero con id: " + empleadoDto.getGeneroId()));

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
                .laboratorioEntity(findLaboratorioEntity)
                .generoEntity(findGeneroEntity)
                .auditoriaEntity(auditoria)
                .build();

        return empleadoRepository.save(empleadoEntity);
    }

    @Override
    public String eliminarEmpleado(Long id) {
        EmpleadoEntity findEntity = empleadoRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún usuario con id: " + id));

        empleadoRepository.deleteEmpleadoById(id);

        return "eliminado";
    }

    @Override
    public EmpleadoEntity obtenerEmpleadoPorId(Long id) {

        return empleadoRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún empleado con id: " + id));
    }

    @Override
    public List<EmpleadoEntity> obtenerEmpleados() {
        return empleadoRepository.findAll();
    }
}
