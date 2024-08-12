package com.api.wslaboratorio.repositories;

import com.api.wslaboratorio.entities.PaqueteEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IPaqueteRepository extends JpaRepository<PaqueteEntity, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM PacienteEntity a WHERE a.pacienteId = :id")
    void deletePaqueteById(@Param("id") Long id);
}