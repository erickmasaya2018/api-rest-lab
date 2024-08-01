package com.api.wslaboratorio.repositories;

import com.api.wslaboratorio.entities.LaboratorioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILaboratorioRepository extends JpaRepository<LaboratorioEntity, Long> {
}