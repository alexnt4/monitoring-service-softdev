package com.monitoring.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "service_health")
public class ServiceHealth {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String serviceName;
    
    @Column(nullable = false)
    private String serviceUrl;
    
    @Enumerated(EnumType.STRING)
    private HealthStatus status;
    
    private Long responseTime;
    
    private String errorMessage;
    
    @Column(name = "checked_at")
    private LocalDateTime checkedAt;
    
    // Constructors
    public ServiceHealth() {}
    
    public ServiceHealth(String serviceName, String serviceUrl, HealthStatus status, 
                        Long responseTime, String errorMessage) {
        this.serviceName = serviceName;
        this.serviceUrl = serviceUrl;
        this.status = status;
        this.responseTime = responseTime;
        this.errorMessage = errorMessage;
        this.checkedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }
    
    public String getServiceUrl() { return serviceUrl; }
    public void setServiceUrl(String serviceUrl) { this.serviceUrl = serviceUrl; }
    
    public HealthStatus getStatus() { return status; }
    public void setStatus(HealthStatus status) { this.status = status; }
    
    public Long getResponseTime() { return responseTime; }
    public void setResponseTime(Long responseTime) { this.responseTime = responseTime; }
    
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    
    public LocalDateTime getCheckedAt() { return checkedAt; }
    public void setCheckedAt(LocalDateTime checkedAt) { this.checkedAt = checkedAt; }
    
    public enum HealthStatus {
        HEALTHY, UNHEALTHY, UNKNOWN
    }
}