package com.api.wslaboratorio.repositories;

import com.api.wslaboratorio.entities.UsuarioEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    Optional<UsuarioEntity> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("DELETE FROM UsuarioEntity a WHERE a.usuarioId = :id")
    void deleteUsuarioById(@Param("id") Long id);
}