package com.api.wslaboratorio.repositories;

import com.api.wslaboratorio.entities.EmpresaEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IEmpresaRepository extends JpaRepository<EmpresaEntity, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM EmpresaEntity a WHERE a.empresaId = :id")
    void deleteEmpresaById(@Param("id") Long id);
}