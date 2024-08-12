package com.api.wslaboratorio.repositories;

import com.api.wslaboratorio.entities.GeneroEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IGeneroRepository extends JpaRepository<GeneroEntity, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM GeneroEntity a WHERE a.generoId = :id")
    void deleteGeneroById(@Param("id") Long id);
}