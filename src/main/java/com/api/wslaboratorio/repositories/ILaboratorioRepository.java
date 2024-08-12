package com.api.wslaboratorio.repositories;

import com.api.wslaboratorio.entities.LaboratorioEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ILaboratorioRepository extends JpaRepository<LaboratorioEntity, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM LaboratorioEntity a WHERE a.laboratorioId = :id")
    void deleteLaboratorioById(@Param("id") Long id);
}