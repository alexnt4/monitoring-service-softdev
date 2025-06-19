# 📊 Stack de Monitoreo con Prometheus y Grafana

Un stack completo de monitoreo para microservicios Spring Boot usando Prometheus para recolección de métricas y Grafana para visualización.

## 🏗️ Arquitectura

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Microservicio │────│   Prometheus    │────│    Grafana      │
│   Spring Boot   │    │  (Recolector)   │    │ (Visualización) │
│   :8080         │    │    :9090        │    │     :3000       │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

## 🚀 Inicio Rápido

### 1. Clonar el repositorio

```bash
git clone https://github.com/alexnt4/monitoring-service-softdev
cd monitoring-stack
```

### 2. Levantar el stack

```bash
docker-compose up -d
```

### 3. Acceder a las herramientas

- **Prometheus**: http://localhost:9090
- **Grafana**: http://localhost:3000
  - Usuario: `admin`
  - Contraseña: `admin123`

## 📁 Estructura del Proyecto

```
monitoring-stack/
├── docker-compose.yml          # Orquestación de contenedores
├── prometheus.yml              # Configuración de Prometheus
├── grafana/                    # Configuraciones de Grafana
│   └── datasources/
│       └── datasource.yml      # Conexión automática con Prometheus
└── README.md
```

## ⚙️ Configuración de Microservicios

Para que tus microservicios Spring Boot sean monitoreados, necesitas configurar actuator:

### 1. Dependencias en `pom.xml`

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
```

### 2. Configuración en `application.properties`

```properties
# Exponer endpoints de actuator
management.endpoints.web.exposure.include=health,info,prometheus
management.endpoints.web.base-path=/actuator
```

### 3. Configuración de seguridad (si usas Spring Security)

```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(auth -> auth
        .requestMatchers("/actuator/health", "/actuator/info", "/actuator/prometheus")
        .permitAll()
        // ... resto de tu configuración
    );
    return http.build();
}
```

### 4. Verificar que funciona

Accede a: `http://localhost:8080/actuator/prometheus`

Deberías ver métricas en formato Prometheus.

## 🔧 Agregar Nuevos Microservicios

Para monitorear un nuevo microservicio, edita `prometheus.yml`:

```yaml
scrape_configs:
  # Tu nuevo microservicio
  - job_name: "nombre-servicio"
    static_configs:
      - targets: ["172.17.0.1:PUERTO"] # Linux
      # - targets: ['host.docker.internal:PUERTO']  # Windows/Mac
    metrics_path: "/actuator/prometheus"
    scrape_interval: 15s
```

Luego reinicia Prometheus:

```bash
docker-compose restart prometheus
```

## 📈 Configurar Dashboards en Grafana

### Opción 1: Dashboards Pre-construidos

1. Ve a **Dashboards** → **Import**
2. Usa estos IDs populares:
   - `11378` - Spring Boot Statistics
   - `4701` - JVM (Micrometer)
   - `10280` - Spring Boot Observability

### Opción 2: Dashboard Personalizado

1. **Dashboards** → **New Dashboard**
2. **Add Visualization**
3. Usa queries como:

   ```promql
   # Requests por segundo
   rate(http_server_requests_seconds_count[5m])

   # Uso de memoria JVM
   jvm_memory_used_bytes{area="heap"}

   # Conexiones de base de datos
   hikaricp_connections_active
   ```

## 🛠️ Comandos Útiles

### Docker Compose

```bash
# Levantar el stack
docker-compose up -d

# Ver logs
docker-compose logs -f

# Ver logs de un servicio específico
docker-compose logs -f prometheus
docker-compose logs -f grafana

# Reiniciar un servicio
docker-compose restart prometheus

# Parar todo
docker-compose down

# Parar y eliminar volúmenes
docker-compose down -v
```

### Verificación de Conectividad

```bash
# Verificar que Prometheus puede acceder a tu microservicio
curl http://172.17.0.1:8080/actuator/prometheus

# Ver targets en Prometheus
curl http://localhost:9090/api/v1/targets
```

## 🔍 Métricas Disponibles

### Métricas de Aplicación

- `http_server_requests_seconds_count` - Número de requests HTTP
- `http_server_requests_seconds_sum` - Tiempo total de requests
- `application_started_time_seconds` - Tiempo de inicio de la aplicación

### Métricas de JVM

- `jvm_memory_used_bytes` - Memoria usada
- `jvm_memory_max_bytes` - Memoria máxima disponible
- `jvm_gc_pause_seconds` - Tiempo de garbage collection

### Métricas de Base de Datos (HikariCP)

- `hikaricp_connections_active` - Conexiones activas
- `hikaricp_connections_idle` - Conexiones inactivas
- `hikaricp_connections_pending` - Conexiones pendientes

## 🚨 Solución de Problemas

### Problema: Prometheus no puede conectarse al microservicio

**Síntoma**: Query `up` muestra `0` para tu servicio

**Solución**:

1. Verificar que el microservicio esté corriendo
2. Verificar que `/actuator/prometheus` responda
3. Usar la IP correcta en `prometheus.yml`:
   - Linux: `172.17.0.1:8080`
   - Windows/Mac: `host.docker.internal:8080`

### Problema: No aparecen métricas en Grafana

**Solución**:

1. Verificar conexión con Prometheus en **Configuration** → **Data Sources**
2. Probar queries básicas en **Explore**: `up`, `prometheus_build_info`
3. Verificar que los targets estén UP en `http://localhost:9090/targets`

### Problema: Error de permisos en carpetas

**Solución**:

```bash
# Dar permisos a las carpetas de Grafana
chmod -R 755 grafana/
```

## 🌐 Acceso Externo

Para acceder desde otras máquinas, modifica `docker-compose.yml`:

```yaml
grafana:
  ports:
    - "0.0.0.0:3000:3000" # Acceso desde cualquier IP

prometheus:
  ports:
    - "0.0.0.0:9090:9090" # Acceso desde cualquier IP
```

## 📚 Referencias

- [Prometheus Documentation](https://prometheus.io/docs/)
- [Grafana Documentation](https://grafana.com/docs/)
- [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html)
- [Micrometer Documentation](https://micrometer.io/docs)

## 🤝 Contribuir

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -am 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.
