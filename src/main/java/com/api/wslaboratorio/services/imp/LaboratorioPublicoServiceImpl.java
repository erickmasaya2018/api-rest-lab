package com.api.wslaboratorio.services.imp;

import com.api.wslaboratorio.dto.salida.HorarioSalidaDto;
import com.api.wslaboratorio.dto.salida.LaboratorioPublicoSalidaDto;
import com.api.wslaboratorio.entities.LaboratorioEntity;
import com.api.wslaboratorio.services.ILaboratorioPublicoService;
import com.api.wslaboratorio.services.ILaboratorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LaboratorioPublicoServiceImpl implements ILaboratorioPublicoService {

    private final ILaboratorioService laboratorioService;

    @Override
    public List<LaboratorioPublicoSalidaDto> obtenerLaboratorioPublicos() {
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        List<LaboratorioEntity> laboratorios = laboratorioService.obtenerLaboratorios();

        List<LaboratorioPublicoSalidaDto> lstLaboratorios = new ArrayList<>();
        List<HorarioSalidaDto> lstHorarios = new ArrayList<>();

        laboratorios.forEach(laboratorioEntity -> {
            LaboratorioPublicoSalidaDto laboratorioPublicoSalidaDto = new LaboratorioPublicoSalidaDto();

            laboratorioEntity.getHorarioLaborarioEntities().forEach(hr -> {
                HorarioSalidaDto horarioSalidaDto = new HorarioSalidaDto();
                horarioSalidaDto.setDia(hr.getDiaSemana());
                horarioSalidaDto.setHora(hr.getHoraAbre().toString() + "-" + hr.getHoraCierra().toString());
                lstHorarios.add(horarioSalidaDto);
            });
            laboratorioPublicoSalidaDto.setLaboratorioId(laboratorioEntity.getLaboratorioId());
            laboratorioPublicoSalidaDto.setDireccion(laboratorioEntity.getDireccion());
            laboratorioPublicoSalidaDto.setNombre(laboratorioEntity.getNombre());
            laboratorioPublicoSalidaDto.setHorarios(lstHorarios);

            lstLaboratorios.add(laboratorioPublicoSalidaDto);
        });

        return lstLaboratorios;
    }
}
