package com.monitoring.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfig {

    @Bean
    public Counter serviceHealthCheckCounter(MeterRegistry meterRegistry) {
        return Counter.builder("service_health_checks_total")
                .description("Total number of health checks performed")
                .tag("type", "health_check")
                .register(meterRegistry);
    }

    @Bean
    public Timer serviceResponseTimer(MeterRegistry meterRegistry) {
        return Timer.builder("service_response_time")
                .description("Response time of monitored services")
                .register(meterRegistry);
    }

    @Bean
    public Counter serviceErrorCounter(MeterRegistry meterRegistry) {
        return Counter.builder("service_errors_total")
                .description("Total number of service errors")
                .register(meterRegistry);
    }
}