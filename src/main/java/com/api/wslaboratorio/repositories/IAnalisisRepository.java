package com.api.wslaboratorio.repositories;

import com.api.wslaboratorio.entities.AnalisisEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IAnalisisRepository extends JpaRepository<AnalisisEntity, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM AnalisisEntity a WHERE a.analisisId = :id")
    void deleteAnalisisById(@Param("id") Long id);
}