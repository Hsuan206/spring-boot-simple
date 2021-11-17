package com.example.employeemanager.repository;

import com.example.employeemanager.model.Config;
import com.example.employeemanager.model.ConfigPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConfigRepo extends JpaRepository<Config, ConfigPK> {
//    Optional<Config> findConfigById(Long id);
    List<Config> findConfigById(ConfigPK id);
}