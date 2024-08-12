package com.api.wslaboratorio.repositories;

import com.api.wslaboratorio.entities.GrupoEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IGrupoRepository extends JpaRepository<GrupoEntity, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM GrupoEntity a WHERE a.grupoId = :id")
    void deleteGrupoById(@Param("id") Long id);
}