
# UM.haberes.core-service

![Java](https://img.shields.io/badge/Java-25-ED8B00?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.2-6DB33F?logo=springboot&logoColor=white)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025.1.4-6DB33F?logo=spring&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?logo=mysql&logoColor=white)
![Apache POI](https://img.shields.io/badge/Apache%20POI-6.0.0-D22128)
![OpenPDF](https://img.shields.io/badge/OpenPDF-3.0.5-blue)
![Springdoc OpenAPI](https://img.shields.io/badge/Springdoc%20OpenAPI-3.1.1-85EA2D?logo=swagger&logoColor=black)
![Docker](https://img.shields.io/badge/Docker-2496ED?logo=docker&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-2088FF?logo=githubactions&logoColor=white)

Servicio central de liquidaciones de haberes de la Universidad de Mendoza. Permite la gestión integral de liquidaciones individuales y masivas, acreditaciones, reportes y administración de personal docente y no docente.

## Versión

**1.22.1** (2026-10-29)
_La versión se corresponde con la declarada en `pom.xml`._

## Tecnologías y dependencias principales

- Java 25
- Spring Boot 4.1.2
- Spring Cloud 2025.1.4 (OpenFeign, Consul)
- Spring Data JPA
- Apache POI 6.0.0 (Excel)
- OpenPDF 3.0.5 (PDF)
- Logback
- Caffeine Cache
- Jackson
- MySQL Connector/J 26.10.0
- Docker
- Springdoc OpenAPI 3.1.1

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
- `docs/diagrams/flujo-bono-individual.mmd`
- `docs/diagrams/flujo-bono-dependencia.mmd`

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

Auditoría de operaciones:
- `docs/diagrams/flujo-auditoria.mmd`

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


## Auditoría de operaciones

`haberes-core` emite eventos de auditoría a `haberes-auditoria` (base `haberes_aud`).

- **Captura**: listeners de Hibernate (`post-insert`/`post-update`/`post-delete`) + un evento
  `REQUEST` por operación HTTP (incluye intentos fallidos).
- **Entrega**: tabla `audit_outbox` en la base `haberes`, escrita en la **misma transacción**
  del cambio; un sender programado la envía por lote con reintentos/backoff.
- **Esquema**: `sql/001_audit_outbox.sql` se aplica a mano (la app no migra: `ddl-auto: none`).
- **Identidad**: headers `X-Usuario-Legajo` (o `X-Legajo-Solicitante`) y `X-Usuario-Login`.
  Sin JWT todavía, el evento se marca con `actor_trust=CLIENT`.

| Variable | Default | Descripción |
|---|---|---|
| `HABERES_AUDIT_MODE` | `OFF` | `OFF` / `LOG` (mide sin enviar) / `SEND` (outbox + HTTP) |
| `HABERES_AUDIT_URL` | `http://localhost:8210` | URL del servicio de auditoría |
| `HABERES_AUDIT_API_KEY` | *(vacío)* | `X-API-Key` para la ingesta |
| `HABERES_AUDIT_REQUEST_EVENTS` | `true` | Emitir un evento por operación |
| `HABERES_AUDIT_CHANGE_EVENTS` | `true` | Emitir un evento por cambio de entidad |
| `HABERES_AUDIT_MASK_FIELDS` | `password,cbu,cuenta,documento` | Campos que se enmascaran |
| `HABERES_AUDIT_EXCLUDED_ENTITIES` | `BuildEntity,ConversionLogEntity` | Entidades no auditadas |
| `HABERES_AUDIT_MAX_ATTEMPTS` | `10` | Intentos antes de marcar `FAILED` |
| `HABERES_AUDIT_BATCH_SIZE` | `200` | Eventos por lote del sender |
| `HABERES_AUDIT_SENDER_DELAY_MS` | `2000` | Espera entre corridas del sender |
| `HABERES_AUDIT_SENDER_INITIAL_DELAY_MS` | `5000` | Espera inicial antes de la primera corrida |
| `HABERES_AUDIT_MAX_PAYLOAD_CHARS` | `32768` | Tope del payload serializado por evento |
| `HABERES_AUDIT_BULK_ENABLED` | `true` | Resume operaciones masivas (`SUMMARY/BULK`); `false` = fila por fila |
| `HABERES_AUDIT_SUMMARY_ENTITIES` | `ItemEntity,ItemVersionEntity,CargoLiquidacionEntity,CargoLiquidacionVersionEntity` | Entidades elegibles para resumirse dentro de un scope |

### Regla de auditoría

Toda escritura de negocio debe pasar por el ciclo de vida de una entidad JPA (`save`,
`delete`, `deleteById`, `deleteAll` con entidades o un derived delete **sin** `@Modifying`)
para que Hibernate dispare `PostInsert`/`PostUpdate`/`PostDelete`. Queda prohibido cualquier
mecanismo que ejecute DML salteando esos listeners:

- `@Modifying` y `@Query` (JPQL o nativo)
- `JdbcTemplate` / `NamedParameterJdbcTemplate`
- `EntityManager.createNativeQuery(...)` / `createMutationQuery(...)`
- `StatelessSession`
- `deleteAllInBatch` / `deleteAllByIdInBatch`
- `@SQLDelete` / `@SQLInsert` / `@SQLUpdate`
- `executeUpdate()` sobre JPQL/nativo

**Única excepción:** el paquete `um.haberes.core.audit`, que escribe el outbox con JDBC a
propósito, y los scripts de `sql/`. No aplica a datos de negocio.

Los deletes derivados no usan `@Modifying`: Spring Data carga las entidades y las borra una
por una (`select` + `EntityManager.remove`), que es justamente lo que permite auditar el
borrado con su `deletedState`. El costo se acota con el batching de Hibernate
(`hibernate.jdbc.batch_size: 200`) y, para períodos muy grandes, con borrado por chunks.

El cumplimiento no depende de la documentación: `AuditCoverageGuardTest` escanea
`src/main/java` en cada `mvn test` y falla con `archivo:línea:patrón` si reaparece alguno de
esos patrones (ignora comentarios y excluye el paquete `audit`). Ver `AGENTS.md`.

### Operaciones masivas (`SUMMARY`/`BULK`)

Un recálculo de liquidación escribe y borra miles de filas de `item`/`item_version`. Para no
generar un evento por fila, el caso de uso abre un scope dentro de su transacción:

```java
try (AuditBulkScope ignored = auditBulk.begin(
        BulkContext.of("recalculo-legajo", anho, mes, legajoId))) {
    itemRepository.deleteAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
    itemRepository.saveAll(items);
}
```

Al cerrar, se emite **un evento por entidad y operación** con `level=SUMMARY`,
`operation=BULK`, `affectedRows` y `metadata.counts` (más período, legajo y `bulkId`). Sólo se
resumen las entidades de `HABERES_AUDIT_SUMMARY_ENTITIES`; el resto de la transacción mantiene
su evento `CHANGE`.

- Fuera del scope (o con `HABERES_AUDIT_BULK_ENABLED=false`) la misma entidad se audita fila
  por fila: el resumen nunca silencia una escritura.
- El borrado del período completo (`DELETE /item/periodo/{anho}/{mes}`) va en chunks de 1.000,
  con una transacción y un evento por chunk que comparten `bulkId`.
- `metadata.counts` permite conciliar el resumen con las filas afectadas
  (`SUM(affected_rows)` por transacción).

### Versionado de cargos

Cada generación de `cargo_liquidacion` escribe **siempre** una copia en
`cargo_liquidacion_version` (sólo inserts; no hay flag). El `version` es el siguiente número por
legajo/período (`MAX+1`); el endpoint legacy `PUT /cargoliquidacion/saveall/{version}` conserva
la versión explícita que recibe.

Como la generación reemplaza el juego completo del legajo/período (borra y reinserta), el
snapshot anterior es el estado previo y permite reconstruir los borrados que resume la
auditoría. Antes de desplegar esta versión, aplicar
`sql/002_cargo_liquidacion_version_index.sql`; sin ese índice, el cálculo de `MAX(version)`
escanea la tabla histórica (1M+ filas). Además, `sql/003_drop_legacy_cargo_version_index.sql`
elimina el índice viejo `legajo_id`, redundante frente a
`idx_cargo_liq_version_legajo_periodo`.

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
- `GET /api/haberes/core/persona/liquidado/{anho}/{mes}` - Personas con liquidación en el período (ordenadas por nombre)
- `POST /api/haberes/core/persona/upload` - Cargar contactos de personas desde Excel

### Consultas por período
- `GET /api/haberes/core/cargoliquidacion/periodo/{anho}/{mes}` - Cargos activos del período
- `GET /api/haberes/core/cargoliquidacion/periodo/{anho}/{mes}/todos` - Todos los cargos del período (sin filtro de situación)
- `GET /api/haberes/core/cargoclasedetalle/periodo/{anho}/{mes}` - Detalle de cargos de clase del período
- `GET /api/haberes/core/liquidacion-adicional/periodo/{anho}/{mes}` - Liquidaciones adicionales del período
- `GET /api/haberes/core/novedadacumulado/codigo/{codigoId}/{anho}/{mes}` - Novedades acumuladas por código y período
- `GET /api/haberes/core/legajocursoCantidad/periodo/{anho}/{mes}` - Cantidad de cursos por legajo en el período
- `GET /api/haberes/core/cursofusion/periodo/{anho}/{mes}` - Fusiones de cursos del período

### Reportes
- `POST /api/haberes/core/libroSueldo/generate/{anho}/{mes}` - Generar libro de sueldo
- `GET /api/haberes/core/sheet/generateitems/{anho}/{mes}` - Generar planilla de items
- `POST /api/haberes/core/bono/dependencia/{dependenciaId}/{anho}/{mes}/{salida}/print-prepare` - Preparar y auditar la impresión masiva de bonos de una dependencia


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
