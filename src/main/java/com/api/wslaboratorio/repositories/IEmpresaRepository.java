package com.api.wslaboratorio.repositories;

import com.api.wslaboratorio.entities.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmpresaRepository extends JpaRepository<EmpresaEntity, Long> {
}