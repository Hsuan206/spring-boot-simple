package com.example.employeemanager.repository;

import com.example.employeemanager.model.Config;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfigRepo extends JpaRepository<Config, Long> {
    Optional<Config> findConfigById(Long id);
}