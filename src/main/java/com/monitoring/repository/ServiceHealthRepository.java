package com.monitoring.repository;

import com.monitoring.model.ServiceHealth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ServiceHealthRepository extends JpaRepository<ServiceHealth, Long> {
    
    List<ServiceHealth> findByServiceNameOrderByCheckedAtDesc(String serviceName);
    
    @Query("SELECT sh FROM ServiceHealth sh WHERE sh.checkedAt >= :since ORDER BY sh.checkedAt DESC")
    List<ServiceHealth> findRecentHealthChecks(@Param("since") LocalDateTime since);
    
    @Query("SELECT DISTINCT sh.serviceName FROM ServiceHealth sh")
    List<String> findDistinctServiceNames();
    
    ServiceHealth findTopByServiceNameOrderByCheckedAtDesc(String serviceName);
}