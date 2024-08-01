package com.api.wslaboratorio.repositories;

import com.api.wslaboratorio.entities.GrupoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGrupoRepository extends JpaRepository<GrupoEntity, Long> {
}