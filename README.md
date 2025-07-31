# UM Haberes Core Service

Servicio core del sistema de Haberes de la Universidad de Mendoza, diseñado para manejar liquidaciones, acreditaciones y gestión de personal docente y no docente.

## Versión

**1.1.0** (2025-07-31)

## Principales dependencias

- Spring Boot: 3.5.3
- Spring Cloud: 2025.0.0
- Kotlin: 2.2.0
- Java: 24

## Diagramas principales

- Arquitectura: `docs/diagrams/arquitectura-general.mmd`
- Flujo de liquidación de sueldos: `docs/diagrams/flujo-liquidacion-sueldos.mmd`
- Modelo entidad-relación: `docs/diagrams/modelo-entidad-relacion.mmd`
- Despliegue: `docs/diagrams/despliegue.mmd`
- **Nuevo:** Flujo de liquidación general masiva: `docs/diagrams/flujo-liquidacion-general.mmd`

## Documentación automática

La documentación y los diagramas se generan automáticamente en cada push a la rama principal mediante GitHub Actions.

---

# UM.haberes.core-service

Servicio core del sistema de Haberes de la Universidad de Mendoza, diseñado para manejar liquidaciones, acreditaciones y gestión de personal docente y no docente.

## Características Principales

- Gestión completa de liquidaciones de haberes (individual y general masiva)
- Manejo de designaciones docentes y no docentes
- Procesamiento de acreditaciones
- Integración con AFIP para reportes fiscales
- Generación de reportes en múltiples formatos (PDF, Excel)
- Sistema de caché para optimización de rendimiento
- Auditoría automática de operaciones
- API REST documentada con OpenAPI/Swagger
- **Nuevo:** Liquidación general masiva con seguimiento de progreso y endpoints dedicados
- **Nuevo:** Procesamiento asíncrono de liquidaciones con arquitectura basada en procesos y estados

## Tecnologías

- Java 21
- Kotlin 2.1.21
- Spring Boot 3.5.0
- Spring Cloud 2025.0.0
- Spring Data JPA
- Spring Security
- Spring Cloud Netflix (Eureka, Feign)
- Apache POI 5.4.1 (para reportes Excel)
- LibrePDF 2.2.1 (para reportes PDF)
- Log4j2
- Caffeine Cache
- Jackson
- MySQL 9.3.0
- Docker

## Requisitos

- JDK 21 o superior
- Maven 3.8.8+
- Docker (opcional)
- MySQL 8.0+

## Configuración

### Variables de Entorno

```yaml
spring:
  application:
    name: um-haberes-core-service
  datasource:
    url: jdbc:mysql://localhost:3306/haberes
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: false
  cloud:
    discovery:
      enabled: true

server:
  port: 8080
  servlet:
    context-path: /api/v1

eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
  instance:
    prefer-ip-address: true

logging:
  level:
    root: INFO
    um.haberes.core: DEBUG
  file:
    name: logs/haberes-core.log
```

### Docker

```bash
# Construir la imagen
docker build -t um-haberes-core-service .

# Ejecutar el contenedor
docker run -d \
  --name haberes-core \
  -p 8080:8080 \
  -e DB_USERNAME=haberes \
  -e DB_PASSWORD=secret \
  um-haberes-core-service
```

## Endpoints Principales

### Liquidaciones
- `POST /liquidaciones` - Generar nueva liquidación
- `GET /liquidaciones/{id}` - Obtener liquidación
- `PUT /liquidaciones/{id}` - Actualizar liquidación
- `DELETE /liquidaciones/{id}` - Eliminar liquidación

### Cargos
- `POST /cargos` - Crear nuevo cargo
- `GET /cargos/{id}` - Obtener cargo
- `PUT /cargos/{id}` - Actualizar cargo
- `GET /cargos/docente/{legajoId}` - Obtener cargos docentes
- `GET /cargos/no-docente/{legajoId}` - Obtener cargos no docentes

### Categorías
- `GET /categorias` - Listar categorías
- `POST /categorias` - Crear categoría
- `PUT /categorias/{id}` - Actualizar categoría
- `GET /categorias/docente` - Listar categorías docentes
- `GET /categorias/no-docente` - Listar categorías no docentes

### Reportes
- `GET /reportes/liquidacion/{id}` - Generar reporte de liquidación
- `GET /reportes/cargos` - Generar reporte de cargos
- `GET /reportes/categorias` - Generar reporte de categorías

## Arquitectura

El servicio sigue una arquitectura hexagonal con las siguientes capas:

- **API Layer**: Controladores REST y DTOs
- **Domain Layer**: Modelos de dominio y lógica de negocio
- **Infrastructure Layer**: Repositorios, servicios externos y configuración

### Patrones de Diseño

- Repository Pattern
- Service Layer Pattern
- Factory Pattern
- Strategy Pattern
- Observer Pattern (para eventos de auditoría)

## Seguridad

- Autenticación mediante JWT
- Autorización basada en roles
- Validación de datos de entrada
- Sanitización de datos
- Logs de auditoría
- Cifrado de datos sensibles

## Monitoreo

- Métricas con Spring Boot Actuator
- Health checks
- Logs estructurados
- Trazas distribuidas
- Monitoreo de caché

## Contribución

1. Fork el repositorio
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## Licencia

Este proyecto es privado y confidencial. Todos los derechos reservados.

## Contacto

Universidad de Mendoza - Departamento de Sistemas
- Email: sistemas@um.edu.ar
- Web: https://www.um.edu.ar
