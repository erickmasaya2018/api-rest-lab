package com.api.wslaboratorio.repositories;

import com.api.wslaboratorio.entities.EstadoEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IEstadoRepository extends JpaRepository<EstadoEntity, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM EstadoEntity a WHERE a.estadoId = :id")
    void deleteEstadoById(@Param("id") Long id);
}