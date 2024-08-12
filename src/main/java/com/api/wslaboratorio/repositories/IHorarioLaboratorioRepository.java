package com.api.wslaboratorio.repositories;

import com.api.wslaboratorio.entities.HorarioLaborarioEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IHorarioLaboratorioRepository extends JpaRepository<HorarioLaborarioEntity, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM HorarioLaborarioEntity a WHERE a.horarioLaboratorioId = :id")
    void deleteHorarioLaboratorioById(@Param("id") Long id);
}