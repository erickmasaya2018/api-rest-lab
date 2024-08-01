package com.api.wslaboratorio.repositories;

import com.api.wslaboratorio.entities.AnalisisEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAnalisisRepository extends JpaRepository<AnalisisEntity, Long> {
}