package com.api.wslaboratorio.services.imp;

import com.api.wslaboratorio.dto.GeneroDto;
import com.api.wslaboratorio.entities.AuditoriaEntity;
import com.api.wslaboratorio.entities.GeneroEntity;
import com.api.wslaboratorio.repositories.IGeneroRepository;
import com.api.wslaboratorio.services.IGeneroService;
import com.api.wslaboratorio.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GeneroImpl implements IGeneroService {
    private final IGeneroRepository generoRepository;
    private final JwtService jwtService;

    @Override
    public GeneroEntity crearGenero(GeneroDto generoDto, HttpServletRequest request) {
        AuditoriaEntity auditoria = AuditoriaEntity.builder()
                .usuarioCreacion(jwtService.extractUsername(request.getHeader("Authorization").substring(7)))
                .fechaCreacion(new Date())
                .build();

        GeneroEntity generoEntity = GeneroEntity.builder()
                .descripcion(generoDto.getDescripcion())
                .abreviatura(generoDto.getAbreviatura())
                .auditoriaEntity(auditoria)
                .build();
        return generoRepository.save(generoEntity);
    }

    @Override
    public GeneroEntity editarGenero(Long id, GeneroDto generoDto, HttpServletRequest request) {
        Optional<GeneroEntity> findEntity = generoRepository.findById(id);

        if (!findEntity.isPresent()) {
            return null;
        }

        AuditoriaEntity auditoria = AuditoriaEntity.builder()
                .usuarioModificacion(jwtService.extractUsername(request.getHeader("Authorization").substring(7)))
                .fechaModificacion(new Date())
                .usuarioCreacion(findEntity.get().getAuditoriaEntity().getUsuarioCreacion())
                .fechaCreacion(new Date())
                .build();

        GeneroEntity generoEntity = GeneroEntity.builder()
                .generoId(findEntity.get().getGeneroId())
                .descripcion(generoDto.getDescripcion())
                .abreviatura(generoDto.getAbreviatura())
                .auditoriaEntity(auditoria)
                .build();

        return generoRepository.save(generoEntity);
    }

    @Override
    public String eliminarGenero(Long id) {
        GeneroEntity findEntity = generoRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún usuario con id: " + id));

        generoRepository.deleteGeneroById(id);
        return "eliminado";
    }

    @Override
    public GeneroEntity obtenerGeneroPorId(Long id) {
        return generoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún genero con id: " + id));
    }

    @Override
    public List<GeneroEntity> obtenerGeneros() {
        return generoRepository.findAll();
    }
}
