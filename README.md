
# UM.haberes.core-service

Servicio central de liquidaciones de haberes de la Universidad de Mendoza. Permite la gestión integral de liquidaciones individuales y masivas, acreditaciones, reportes y administración de personal docente y no docente.

## Versión

**1.2.0** (2025-09-28)
_La versión se corresponde con la declarada en `pom.xml`._

## Tecnologías y dependencias principales

- Java 24
- Kotlin 2.2.0
- Spring Boot 3.5.3
- Spring Cloud 2025.0.0 (OpenFeign, Consul)
- Spring Data JPA
- Spring Security
- Apache POI 5.4.1 (Excel)
- LibrePDF 2.2.4 (PDF)
- Log4j2
- Caffeine Cache
- Jackson
- MySQL Connector/J 9.3.0
- Docker
- Springdoc OpenAPI

## Diagramas principales

- Arquitectura: `docs/diagrams/arquitectura-general.mmd`
- Flujo de liquidación de sueldos: `docs/diagrams/flujo-liquidacion-sueldos.mmd`
- Modelo entidad-relación: `docs/diagrams/modelo-entidad-relacion.mmd`
- Despliegue: `docs/diagrams/despliegue.mmd`
- **Nuevo:** Flujo de liquidación general masiva: `docs/diagrams/flujo-liquidacion-general.mmd`

## Documentación automática

La documentación y los diagramas se generan automáticamente en cada push a la rama principal mediante GitHub Actions y se publican en GitHub Pages.

---


## Funcionalidades principales

- Liquidación de haberes individual y general masiva (procesamiento asíncrono y seguimiento de progreso)
- Gestión de designaciones y cargos docentes/no docentes
- Procesamiento de acreditaciones
- Generación de reportes en PDF y Excel
- API REST documentada con OpenAPI
- Auditoría y logs de operaciones
- Integración con Consul y OpenFeign para servicios distribuidos


## Requisitos

- JDK 24
- Maven 3.8.8+
- Docker (opcional)
- MySQL 8.0+


## Configuración básica

### Variables de entorno recomendadas

eureka:
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
    consul:
      host: localhost
      port: 8500

server:
  port: 8080
  servlet:
    context-path: /api/v1

logging:
  level:
    root: INFO
    um.haberes.core: DEBUG
  file:
    name: logs/haberes-core.log
```


## Docker

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


## Endpoints principales (ejemplo)

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

El servicio sigue una arquitectura hexagonal:

- **API Layer**: Controladores REST y DTOs
- **Domain Layer**: Modelos de dominio y lógica de negocio
- **Infrastructure Layer**: Repositorios, servicios externos y configuración


### Patrones de diseño utilizados

- Repository
- Service Layer
- Factory
- Strategy
- Observer (para eventos de auditoría)


## Seguridad

- Autenticación JWT
- Autorización por roles
- Validación y sanitización de datos
- Logs de auditoría
- Cifrado de datos sensibles


## Monitoreo

- Métricas con Spring Boot Actuator
- Health checks
- Logs estructurados
- Trazas distribuidas
- Monitoreo de caché


## Contribución

1. Haz fork del repositorio
2. Crea una rama para tu feature (`git checkout -b feature/mi-feature`)
3. Realiza tus cambios y commitea (`git commit -m 'feat: agrega mi feature'`)
4. Haz push a tu rama (`git push origin feature/mi-feature`)
5. Abre un Pull Request


## Licencia

Este proyecto es privado y confidencial. Todos los derechos reservados.


## Contacto

Universidad de Mendoza - Departamento de Sistemas
- Email: sistemas@um.edu.ar
- Web: https://www.um.edu.ar
