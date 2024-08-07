package com.api.wslaboratorio.repositories;

import com.api.wslaboratorio.entities.CitaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICitaRepository extends JpaRepository<CitaEntity, Long> {
}