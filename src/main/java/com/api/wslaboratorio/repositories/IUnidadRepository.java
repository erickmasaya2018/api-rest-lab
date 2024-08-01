package com.api.wslaboratorio.repositories;

import com.api.wslaboratorio.entities.UnidadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUnidadRepository extends JpaRepository<UnidadEntity, Long> {
}