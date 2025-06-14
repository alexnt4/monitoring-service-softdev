package com.monitoring.model;

import jakarta.persistence.*;

@Entity
@Table(name = "monitored_services")
public class MonitoredService {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String name;
    
    @Column(nullable = false)
    private String baseUrl;
    
    @Column(name = "health_endpoint")
    private String healthEndpoint = "/actuator/health";
    
    @Column(name = "metrics_endpoint")
    private String metricsEndpoint = "/actuator/prometheus";
    
    private boolean active = true;
    
    @Column(name = "check_interval")
    private int checkInterval = 30; // segundos
    
    // Constructors
    public MonitoredService() {}
    
    public MonitoredService(String name, String baseUrl) {
        this.name = name;
        this.baseUrl = baseUrl;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getBaseUrl() { return baseUrl; }
    public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }
    
    public String getHealthEndpoint() { return healthEndpoint; }
    public void setHealthEndpoint(String healthEndpoint) { this.healthEndpoint = healthEndpoint; }
    
    public String getMetricsEndpoint() { return metricsEndpoint; }
    public void setMetricsEndpoint(String metricsEndpoint) { this.metricsEndpoint = metricsEndpoint; }
    
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    
    public int getCheckInterval() { return checkInterval; }
    public void setCheckInterval(int checkInterval) { this.checkInterval = checkInterval; }
    
    public String getHealthUrl() {
        return baseUrl + healthEndpoint;
    }
    
    public String getMetricsUrl() {
        return baseUrl + metricsEndpoint;
    }
}