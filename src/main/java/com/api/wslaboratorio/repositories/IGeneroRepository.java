package com.api.wslaboratorio.repositories;

import com.api.wslaboratorio.entities.GeneroEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGeneroRepository extends JpaRepository<GeneroEntity, Long> {
}