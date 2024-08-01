package com.api.wslaboratorio.repositories;

import com.api.wslaboratorio.entities.EmpleadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmpleadoRepository extends JpaRepository<EmpleadoEntity, Long> {
}