
# UM.haberes.core-service

Servicio central de liquidaciones de haberes de la Universidad de Mendoza. Permite la gestión integral de liquidaciones individuales y masivas, acreditaciones, reportes y administración de personal docente y no docente.

## Versión

**1.15.0** (2026-09-20)
_La versión se corresponde con la declarada en `pom.xml`._

## Tecnologías y dependencias principales

- Java 25
- Spring Boot 4.1.1
- Spring Cloud 2025.1.3 (OpenFeign, Consul)
- Spring Data JPA
- Apache POI 5.5.1 (Excel)
- OpenPDF 3.0.5 (PDF)
- Logback
- Caffeine Cache
- Jackson
- MySQL Connector/J 26.7.0
- Docker
- Springdoc OpenAPI 3.1.0

## Diagramas principales

Arquitectura y modelo:
- `docs/diagrams/arquitectura-general.mmd`
- `docs/diagrams/contextos-hexagonales.mmd`
- `docs/diagrams/arquitectura-slice-hexagonal.mmd`
- `docs/diagrams/modelo-entidad-relacion.mmd`
- `docs/diagrams/despliegue.mmd`

Liquidaciones:
- `docs/diagrams/flujo-liquidacion-sueldos.mmd`
- `docs/diagrams/flujo-liquidacion-general.mmd`
- `docs/diagrams/flujo-liquidacion-ciclo.mmd`
- `docs/diagrams/flujo-cargo-liquidacion.mmd`
- `docs/diagrams/flujo-item-letra.mmd`
- `docs/diagrams/flujo-novedad-gestion.mmd`
- `docs/diagrams/flujo-novedad-file.mmd`
- `docs/diagrams/flujo-acreditacion.mmd`
- `docs/diagrams/flujo-orden-pago.mmd`
- `docs/diagrams/flujo-catalogos-liquidacion.mmd`
- `docs/diagrams/flujo-etec-antiguedad.mmd`
- `docs/diagrams/flujo-cargo-clase.mmd`
- `docs/diagrams/flujo-liquidacion-adicional.mmd`

Personas y cursos:
- `docs/diagrams/flujo-persona-consultas.mmd`
- `docs/diagrams/flujo-upload-contactos.mmd`
- `docs/diagrams/flujo-dependencia.mmd`
- `docs/diagrams/flujo-cursos.mmd`
- `docs/diagrams/flujo-facultad-geografica.mmd`
- `docs/diagrams/flujo-designacion-tool.mmd`
- `docs/diagrams/flujo-anotacion-aprobacion.mmd`

Contabilidad y reportes:
- `docs/diagrams/flujo-contable-asiento.mmd`
- `docs/diagrams/flujo-contabilidad-imputaciones.mmd`
- `docs/diagrams/flujo-legajobanco-filtro-codigo.mmd`
- `docs/diagrams/flujo-vistas-reporte.mmd`
- `docs/diagrams/flujo-sheets-reportes.mmd`
- `docs/diagrams/flujo-libro-sueldo.mmd`
- `docs/diagrams/flujo-contratados-planilla.mmd`
- `docs/diagrams/flujo-administracion-formularios.mmd`

Maestros e integración:
- `docs/diagrams/flujo-maestros-catalogos.mmd`
- `docs/diagrams/flujo-actividad-contacto.mmd`
- `docs/diagrams/flujo-integracion-externa.mmd`

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

- JDK 25
- Maven 3.8.8+
- Docker (opcional)
- MySQL 8.0+


## Configuración básica

### Variables de entorno recomendadas

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
- `GET /api/haberes/core/liquidacion/{liquidacionId}` - Obtener liquidación
- `GET /api/haberes/core/liquidacion/unique/{legajoId}/{anho}/{mes}` - Obtener liquidación por clave única
- `POST /api/haberes/core/liquidacion/` - Crear liquidación
- `PUT /api/haberes/core/liquidacion/{liquidacionId}` - Actualizar liquidación
- `DELETE /api/haberes/core/liquidacion/periodo/{anho}/{mes}` - Eliminar liquidaciones del período
- `GET /api/haberes/core/makeLiquidacion/legajo/{legajoId}/{anho}/{mes}/{force}` - Liquidar un legajo
- `GET /api/haberes/core/makeLiquidacionGeneral/general/{anho}/{mes}/{force}` - Liquidación general masiva

### Cargos
- `GET /api/haberes/core/cargo/legajo/{legajoId}` - Obtener cargos por legajo
- `GET /api/haberes/core/cargo/{cargoId}` - Obtener cargo
- `POST /api/haberes/core/cargo/` - Crear cargo
- `PUT /api/haberes/core/cargo/{cargoId}` - Actualizar cargo
- `DELETE /api/haberes/core/cargo/{cargoId}` - Eliminar cargo

### Categorías
- `GET /api/haberes/core/categoria/` - Listar categorías
- `GET /api/haberes/core/categoria/{categoriaId}` - Obtener categoría
- `POST /api/haberes/core/categoria/upload/{anho}/{mes}` - Cargar categorías desde Excel
- `GET /api/haberes/core/categoria/nogrado` - Listar categorías sin grado

### Personas
- `GET /api/haberes/core/persona/` - Listar personas
- `GET /api/haberes/core/persona/liquidables` - Listar personas liquidables
- `POST /api/haberes/core/persona/upload` - Cargar contactos de personas desde Excel

### Reportes
- `POST /api/haberes/core/libroSueldo/generate/{anho}/{mes}` - Generar libro de sueldo
- `GET /api/haberes/core/sheet/generateitems/{anho}/{mes}` - Generar planilla de items


## Arquitectura

El servicio sigue una arquitectura hexagonal (puertos y adaptadores):

- **Domain Layer**: Modelos de dominio, puertos de entrada (Use Cases) y puertos de salida (Repositories)
- **Application Layer**: Servicios de aplicación e implementaciones de casos de uso
- **Infrastructure Layer**: Adaptadores JPA, controladores REST, DTOs, mappers y configuración


### Patrones de diseño utilizados

- Hexagonal Architecture (Ports & Adapters)
- Repository
- Service Layer
- Use Case / Interactor
- Mapper (Entity-DTO, Entity-Domain)


## Seguridad

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
