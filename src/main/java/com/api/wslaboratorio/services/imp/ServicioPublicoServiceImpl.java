package com.api.wslaboratorio.services.imp;

import com.api.wslaboratorio.dto.salida.ServicioPublicoSalidaDto;
import com.api.wslaboratorio.entities.AnalisisEntity;
import com.api.wslaboratorio.services.IAnalisisService;
import com.api.wslaboratorio.services.IServicioPublicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicioPublicoServiceImpl implements IServicioPublicoService {

    private final IAnalisisService analisisService;

    @Override
    public List<ServicioPublicoSalidaDto> obtenerServiciosPublicos() {
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        List<AnalisisEntity> listaAnalisis = analisisService.obtenerAnalisis();

        List<ServicioPublicoSalidaDto> lstServiciosPublicos = new ArrayList<>();
        listaAnalisis.forEach(analisisEntity -> {

            ServicioPublicoSalidaDto dto = new ServicioPublicoSalidaDto();
            dto.setServicioId(analisisEntity.getAnalisisId());
            dto.setNombre(analisisEntity.getNombre());
            dto.setDescripcion(analisisEntity.getDescripcion());
            dto.setCosto(analisisEntity.getPrecio());

            lstServiciosPublicos.add(dto);
        });

        return lstServiciosPublicos;
    }
}
