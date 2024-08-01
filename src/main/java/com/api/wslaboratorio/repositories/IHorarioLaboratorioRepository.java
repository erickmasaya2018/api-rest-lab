package com.api.wslaboratorio.repositories;

import com.api.wslaboratorio.entities.HorarioLaborarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IHorarioLaboratorioRepository extends JpaRepository<HorarioLaborarioEntity, Long> {
}