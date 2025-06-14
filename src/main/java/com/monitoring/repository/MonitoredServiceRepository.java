package com.monitoring.repository;

import com.monitoring.model.MonitoredService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MonitoredServiceRepository extends JpaRepository<MonitoredService, Long> {
    
    List<MonitoredService> findByActiveTrue();
    
    Optional<MonitoredService> findByName(String name);
    
    boolean existsByName(String name);
}