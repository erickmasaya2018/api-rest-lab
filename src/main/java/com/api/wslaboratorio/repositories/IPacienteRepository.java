package com.api.wslaboratorio.repositories;

import com.api.wslaboratorio.dto.custom.PacienteCustomDto;
import com.api.wslaboratorio.entities.PacienteEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IPacienteRepository extends JpaRepository<PacienteEntity, Long> {
    @Query(value = "CALL ObtenerAnalisisPorIdPaciente(:pacienteId)", nativeQuery = true)
    PacienteCustomDto ObtenerAnalisisPorIdPaciente(@Param("pacienteId") Long pacienteId);

    @Transactional
    @Modifying
    @Query("DELETE FROM PacienteEntity a WHERE a.pacienteId = :id")
    void deletePacienteById(@Param("id") Long id);
}