package com.api.wslaboratorio.repositories;

import com.api.wslaboratorio.entities.UnidadEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IUnidadRepository extends JpaRepository<UnidadEntity, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM UnidadEntity a WHERE a.unidadId = :id")
    void deleteUnidadById(@Param("id") Long id);
}