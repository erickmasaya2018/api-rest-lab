package com.api.wslaboratorio.repositories;

import com.api.wslaboratorio.entities.EmpleadoEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IEmpleadoRepository extends JpaRepository<EmpleadoEntity, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM EmpleadoEntity a WHERE a.empleadoId = :id")
    void deleteEmpleadoById(@Param("id") Long id);
}