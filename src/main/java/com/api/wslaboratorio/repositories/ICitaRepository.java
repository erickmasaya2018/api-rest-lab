package com.api.wslaboratorio.repositories;

import com.api.wslaboratorio.entities.CitaEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ICitaRepository extends JpaRepository<CitaEntity, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM CitaEntity a WHERE a.citaId = :id")
    void deleteCitaById(@Param("id") Long id);
}