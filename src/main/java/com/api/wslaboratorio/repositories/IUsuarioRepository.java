package com.api.wslaboratorio.repositories;

import com.api.wslaboratorio.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    Optional<UsuarioEntity> findByNombreUsuario(String nombreUsuario);
}