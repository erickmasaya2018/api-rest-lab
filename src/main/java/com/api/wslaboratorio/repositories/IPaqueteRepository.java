package com.api.wslaboratorio.repositories;

import com.api.wslaboratorio.entities.PaqueteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPaqueteRepository extends JpaRepository<PaqueteEntity, Long> {
}