package com.api.wslaboratorio.repositories;

import com.api.wslaboratorio.entities.EstadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEstadoRepository extends JpaRepository<EstadoEntity, Long> {
}