
# UM.haberes.core-service

![Java](https://img.shields.io/badge/Java-25-ED8B00?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.2-6DB33F?logo=springboot&logoColor=white)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025.1.4-6DB33F?logo=spring&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?logo=mysql&logoColor=white)
![Apache POI](https://img.shields.io/badge/Apache%20POI-6.0.0-D22128)
![OpenPDF](https://img.shields.io/badge/OpenPDF-3.0.5-blue)
![Springdoc OpenAPI](https://img.shields.io/badge/Springdoc%20OpenAPI-3.1.1-85EA2D?logo=swagger&logoColor=black)
![Docker](https://img.shields.io/badge/Docker-2496ED?logo=docker&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-2088FF?logo=githubactions&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?logo=apachemaven&logoColor=white)
![JUnit](https://img.shields.io/badge/JUnit-25A162?logo=junit5&logoColor=white)
![Caffeine](https://img.shields.io/badge/Caffeine-blue)

Servicio central de liquidaciones de haberes de la Universidad de Mendoza. Permite la gestión integral de liquidaciones individuales y masivas, acreditaciones, reportes y administración de personal docente y no docente.

## Versión

**3.3.0** (2026-11-25)
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
- `docs/diagrams/flujo-liquidacion-general-proceso.mmd`
- `docs/diagrams/flujo-liquidacion-ciclo.mmd`
- `docs/diagrams/flujo-liquidacion-individual.mmd`
- `docs/diagrams/flujo-cargo-liquidacion.mmd`
- `docs/diagrams/flujo-item-letra.mmd`
- `docs/diagrams/flujo-novedad-gestion.mmd`
- `docs/diagrams/flujo-novedad-file.mmd`
- `docs/diagrams/flujo-acreditacion.mmd`
- `docs/diagrams/flujo-archivo-acreditacion.mmd`
- `docs/diagrams/flujo-orden-pago.mmd`
- `docs/diagrams/flujo-catalogos-liquidacion.mmd`
- `docs/diagrams/flujo-etec-antiguedad.mmd`
- `docs/diagrams/flujo-etec-cargo-puntos.mmd`
- `docs/diagrams/flujo-etec-liquidacion-puntos.mmd`
- `docs/diagrams/flujo-etec-presentismo.mmd`
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
- `docs/diagrams/flujo-sijp.mmd`
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
- Respuestas REST con relaciones anidadas (persona, dependencia, facultad, geográfica, categoría, código y conceptos AFIP) para los clientes que antes resolvían esos datos con consultas adicionales
- Auditoría y logs de operaciones
- Seguridad con RBAC: permisos por módulo, roles, overrides por usuario, tokens de sesión y de servicio
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

haberes:
  liquidacion-general:
    concurrencia: 2
```

`haberes.liquidacion-general.concurrencia` (default `2`) limita los hilos con los que el
proceso masivo de liquidación general procesa los legajos del período.

`haberes.etec.catalogo-estricto` (default `false`) hace que `EtecCatalogoValidator` impida el
arranque si el catálogo no tiene los códigos ETEC (`984` a `990`); con `false` el faltante se
registra como error en el log con los scripts `sql/` que hay que aplicar.


## Auditoría de operaciones

`haberes-core` emite eventos de auditoría a `haberes-auditoria` (base `haberes_aud`).

- **Captura**: listeners de Hibernate (`post-insert`/`post-update`/`post-delete`) + un evento
  `REQUEST` por operación HTTP (incluye intentos fallidos).
- **Entrega**: tabla `audit_outbox` en la base `haberes`, escrita en la **misma transacción**
  del cambio; un sender programado la envía por lote con reintentos/backoff. Una fila sólo
  pasa a `SENT` cuando la respuesta confirma qué pasó con **cada** evento del lote
  (`accepted`/`duplicates`/`rejected`); un cuerpo vacío, no interpretable o incompleto se
  reintenta.
- **Retención**: una limpieza programada (cada hora) purga `SENT` y `FAILED` según su
  retención; `PENDING` nunca se borra y, si envejece, se avisa por log.
- **Esquema**: `sql/001_audit_outbox.sql` se aplica a mano (la app no migra: `ddl-auto: none`).
- **Identidad**: con token se usa el `legajoId` de la `Identidad` autenticada; sin token,
  headers `X-Usuario-Legajo` (o `X-Legajo-Solicitante`) y `X-Usuario-Login`, y el evento se
  marca con `actor_trust=CLIENT`.

| Variable | Default | Descripción |
|---|---|---|
| `HABERES_AUDIT_MODE` | `OFF` | `OFF` / `LOG` (mide sin enviar) / `SEND` (outbox + HTTP) |
| `HABERES_AUDIT_URL` | `http://localhost:8210` | URL del servicio de auditoría |
| `HABERES_AUDIT_API_KEY` | *(vacío)* | `X-API-Key` para la ingesta |
| `HABERES_AUDIT_REQUEST_EVENTS` | `true` | Emitir un evento por operación |
| `HABERES_AUDIT_CHANGE_EVENTS` | `true` | Emitir un evento por cambio de entidad |
| `HABERES_AUDIT_MASK_FIELDS` | `password,cbu,cuenta,documento` | Campos que se enmascaran |
| `HABERES_AUDIT_EXCLUDED_ENTITIES` | `BuildEntity,ConversionLogEntity,LiquidacionGeneralProcesoEntity,LiquidacionGeneralDetalleEntity` | Entidades no auditadas |
| `HABERES_AUDIT_MAX_ATTEMPTS` | `10` | Intentos antes de marcar `FAILED` |
| `HABERES_AUDIT_BATCH_SIZE` | `200` | Eventos por lote del sender |
| `HABERES_AUDIT_SENDER_DELAY_MS` | `2000` | Espera entre corridas del sender |
| `HABERES_AUDIT_SENDER_INITIAL_DELAY_MS` | `5000` | Espera inicial antes de la primera corrida |
| `HABERES_AUDIT_MAX_PAYLOAD_CHARS` | `32768` | Tope del payload serializado por evento |
| `HABERES_AUDIT_SENT_RETENTION_DAYS` | `7` | Días que se conservan las filas `SENT` antes de purgarlas |
| `HABERES_AUDIT_FAILED_RETENTION_DAYS` | `30` | Días que se conservan las `FAILED` desde el fallo (para inspección) |
| `HABERES_AUDIT_PENDING_WARN_HOURS` | `24` | Antigüedad a partir de la cual se avisa que hay `PENDING` sin entregar |
| `HABERES_AUDIT_CLEANUP_DELAY_MS` | `3600000` | Espera entre corridas de la limpieza (cada hora) |
| `HABERES_AUDIT_CLEANUP_INITIAL_DELAY_MS` | `60000` | Espera inicial antes de la primera limpieza |
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
- `GET /api/haberes/core/makeLiquidacion/legajo/{legajoId}/{anho}/{mes}/{force}` - Liquidar un legajo (legacy)
- `GET /api/haberes/core/makeLiquidacionGeneral/general/{anho}/{mes}/{force}` - Liquidación general masiva

En la liquidación individual, la jubilación secundaria ETEC (código `70`, 2% sobre los haberes con
aportes) sólo se emite cuando el legajo tiene al menos un cargo ETEC acumulado y sus horas semanales
de `curso_cargo` en la facultad ETEC (15) superan a las del resto de las facultades; el empate no
aplica y se ignoran los cursos sin curso o sin facultad.

Cada liquidación elimina antes las novedades duplicadas del período. Aplicar
`sql/026_novedad_periodo_duplicado_index.sql` antes de desplegar: la consulta filtra por `anho`/`mes`
con el índice en lugar de recorrer toda la tabla `novedad`.

### Liquidación individual (slice hexagonal)
- `GET /api/haberes/core/liquidacion-individual/{legajoId}/{anho}/{mes}` - Ficha agregada del legajo/período con las acciones habilitadas
- `POST /api/haberes/core/liquidacion-individual/{legajoId}/{anho}/{mes}/cargos` - Regenerar cargos docentes, no docentes y ETEC (puntos y horas) del legajo/período
- `POST /api/haberes/core/liquidacion-individual/{legajoId}/{anho}/{mes}/liquidar` - Liquidar el legajo/período (body opcional `{"forzar": true}`)
- `DELETE /api/haberes/core/liquidacion-individual/{legajoId}/{anho}/{mes}/{forzar}` - Eliminar la liquidación del legajo/período
- `POST /api/haberes/core/liquidacion-individual/{legajoId}/{anho}/{mes}/novedad` - Alta o actualización de una novedad
- `DELETE /api/haberes/core/liquidacion-individual/{legajoId}/{anho}/{mes}/novedad/{novedadId}/{forzar}` - Eliminar una novedad del legajo/período

### Liquidación general (slice hexagonal)
- `GET /api/haberes/core/liquidacion-general/{anho}/{mes}` - Padrón agregado del período: acciones, resumen (docentes, no docentes, novedades, liquidados, bloqueados, neto negativo/cero y total neto) y filas por legajo
- `POST /api/haberes/core/liquidacion-general/{anho}/{mes}/cargos` - Iniciar la generación masiva de cargos (`202` con `procesoId`; `409` si ya hay un proceso en curso)
- `POST /api/haberes/core/liquidacion-general/{anho}/{mes}/liquidar` - Iniciar la liquidación masiva (body opcional `{"forzar": true}`)
- `POST /api/haberes/core/liquidacion-general/{anho}/{mes}/eliminar` - Iniciar la eliminación masiva de liquidaciones (body opcional `{"forzar": true}`)
- `GET /api/haberes/core/liquidacion-general/proceso/{procesoId}` - Estado y avance del proceso (`404` si no existe)
- `GET /api/haberes/core/liquidacion-general/proceso/{procesoId}/detalle/{resultado}` - Resultados por legajo (`LIQUIDADO`, `ELIMINADO`, `GENERADO`, `OMITIDO`, `FALLIDO`)
- `GET /api/haberes/core/liquidacion-general/procesos/{anho}/{mes}` - Procesos del período

Los tres procesos masivos responden `202` con `procesoId`, `409 PROCESO_EN_CURSO` o `200` con
`SIN_CONTROL` (salvo en eliminar) o `SIN_LIQUIDABLES` cuando no se inician. El avance se persiste en
`liquidacion_general_proceso`/`liquidacion_general_detalle` (aplicar
`sql/019_liquidacion_general_proceso.sql` antes de usarlos).

### Seguridad social (SIJP)
- `GET /api/haberes/core/sijp/{anho}/{mes}/{variante}` - Genera el archivo SIJP del período (`variante`: `normal` o `etec`). Devuelve el `.txt` en Base64 (registros de 499 caracteres, CRLF y windows-1252), la cantidad de registros y las listas `omitidos`/`advertencias` (vacías: hoy la generación bloquea en lugar de omitir); responde `400` si la variante no es `normal`/`etec`, `404` si el período no tiene control y `422` si no hay legajos liquidados o alguno no tiene persona/CUIL de 11 dígitos
- `GET /api/haberes/core/makeLiquidacion/generateSIJP/{anho}/{mes}` - Alias legado de la variante normal; escribe `sijp-<anho>-<mes>.txt` en `path.files`

### Acreditación bancaria (archivo Santander y Otros Bancos)
- `POST /api/haberes/core/acreditacion/archivo/santander` - Generar el archivo de acreditación Santander (masivo o individual según el body)
- `POST /api/haberes/core/acreditacion/archivo/otrosbancos` - Generar el archivo de acreditación del resto de los bancos (acuerdo `02`, o `03`/forma de pago `57` con `cci`)
- `POST /api/haberes/core/legajobanco/preparar/{anho}/{mes}` - Preparar las acreditaciones del período completo
- `POST /api/haberes/core/legajobanco/preparar/codigo/{anho}/{mes}/{codigoId}` - Preparar sólo los legajos con novedades del código
- `POST /api/haberes/core/legajobanco/preparar/{legajoId}/{anho}/{mes}` - Preparar un legajo y devolver sus filas

El body común es `{anho, mes, fechaPago, numEnvio, salida, dependenciaId, codigoId, cci, individual, legajos}`;
en el modo individual, `legajos` lleva `{legajoId, cbu, acreditado}`. La respuesta devuelve el archivo en
Base64 (líneas de 650 caracteres, CRLF y windows-1252), la cantidad, el total, los rechazos y si el período
quedó acreditado; responde `422` si ningún legajo pudo acreditarse. El masivo marca la acreditación del
período y genera la orden de pago.

En la planilla contable, `acreditacion.orden_contable` puede quedar en `NULL` mientras el asiento no se
generó: `NULL` significa "todavía sin asiento" y `0` es el valor heredado. Aplicar
`sql/025_acreditacion_orden_contable_null.sql` antes de desplegar, porque es la columna la que hoy
impide dar de alta el período sólo con el límite de novedades (`ddl-auto: none`).

`legajobanco.acreditado` admite `NULL`: el alta lo normaliza a `0`, una actualización que no informa
el campo conserva el valor existente y el `PUT` respeta el `legajoBancoId` del path. El body común
sigue aceptando `acreditado` en el modo individual.

### Novedades (carga masiva y transferencia)
- `POST /api/haberes/core/novedadfile/upload/{anho}/{mes}/{stepByStep}` - Carga la planilla Excel a la tabla de pendientes
- `GET /api/haberes/core/novedadfile/import/{anho}/{mes}` - Importa los pendientes al período (`409` si hay duplicados)
- `POST /api/haberes/core/novedadfile/transfer/{anho}/{mes}` - Transfiere las novedades transferibles del mes anterior y devuelve `{nuevas, actualizadas, sinCambios, total}`. Lee el destino una sola vez, copia una fila por clave (legajo + código + dependencia), reutiliza el `novedadId` existente y no reescribe las filas idénticas, por lo que repetir la operación no genera UPDATE ni eventos de auditoría
- `GET /api/haberes/core/novedadfile/transfer/{anho}/{mes}` - Alias legacy del cliente VB6 (`204`, deprecado); el cliente web usa el `POST`

### Seguridad (RBAC y sesión)
- `GET /api/haberes/core/auth/legajo/{legajoId}` - Datos mínimos de la pantalla de login (`legajoId`, `apellidoNombre`, `habilitado`, `primerIngreso`); no expone documento, CUIL, facultad, permisos ni hash
- `POST /api/haberes/core/auth/login` - Login con body `{legajoId, password, build}`; valida la clave, actualiza `last_log` y devuelve `{token, expiresAt, legajoId, administrador}`
- `GET /api/haberes/core/permisoEfectivo/usuario/{legajoId}` - Permisos efectivos del usuario (`permisos` ordenados y `administrador`); sólo el propio legajo o un administrador
- `GET /api/haberes/core/permiso` (`/activos`, `/aplicacion/{aplicacion}`, `/{permisoId}`), `POST`/`PUT /permiso`, `DELETE /permiso/{permisoId}` - Catálogo de permisos; la baja es lógica (`activo = 0`) para conservar las asignaciones
- `POST /api/haberes/core/permiso/sync` - Reconciliación de `permisos.yaml`/`roles.yaml`; body opcional `{dryRun}` para previsualizar
- `GET`/`POST`/`PUT`/`DELETE /api/haberes/core/rol` - Roles; `ADMIN_HABERES` es protegido y no se edita ni elimina
- `GET /api/haberes/core/rolPermiso/rol/{rolId}`, `POST /api/haberes/core/rolPermiso`, `DELETE /api/haberes/core/rolPermiso/rol/{rolId}/permiso/{permisoId}` - Matriz rol/permiso (`ADMIN_HABERES` se reconcilia desde `roles.yaml`)
- `GET /api/haberes/core/usuarioRol/usuario/{legajoId}`, `POST /api/haberes/core/usuarioRol`, `DELETE /api/haberes/core/usuarioRol/usuario/{legajoId}/rol/{rolId}` - Roles por usuario; `ADMIN_HABERES` se asigna con el flag administrador
- `GET /api/haberes/core/usuarioPermiso/usuario/{legajoId}`, `PUT /api/haberes/core/usuarioPermiso/usuario/{legajoId}/permiso/{permisoId}` con body `{otorgado}` (`1` otorga, `0` revoca, nulo elimina) y `DELETE` del mismo path - Overrides individuales

### Cargos
- `GET /api/haberes/core/cargo/legajo/{legajoId}` - Obtener cargos por legajo
- `GET /api/haberes/core/cargo/{cargoId}` - Obtener cargo
- `POST /api/haberes/core/cargo/` - Crear cargo
- `PUT /api/haberes/core/cargo/{cargoId}` - Actualizar cargo
- `DELETE /api/haberes/core/cargo/{cargoId}` - Eliminar cargo
- `POST /api/haberes/core/cargoliquidacion/reemplazar/periodo/{anho}/{mes}` - Reemplazar atómicamente los cargos del período para un conjunto de categorías
- `POST /api/haberes/core/cargoliquidacion/reemplazar/legajo/{legajoId}/{anho}/{mes}` - Reemplazar atómicamente los cargos de un legajo para un conjunto de categorías
- `POST /api/haberes/core/cargoliquidacion/generacion/legajo/{legajoId}/{anho}/{mes}` - Generar los cargos del legajo/período con todos los universos (docentes, no docentes y ETEC)
- `POST /api/haberes/core/cargoliquidacion/generacion/etec/legajo/{legajoId}/{anho}/{mes}` - Regenerar sólo los cargos ETEC del legajo/período
- `POST /api/haberes/core/cargoliquidacion/generacion/etec/periodo/{anho}/{mes}` - Regenerar sólo los cargos ETEC del período
- `GET /api/haberes/core/cargoliquidacion/generacion/preview/etec/legajo/{legajoId}/{anho}/{mes}` - Previsualizar los cargos ETEC del legajo sin persistir
- `GET /api/haberes/core/cargoliquidacion/generacion/preview/etec/periodo/{anho}/{mes}` - Previsualizar los cargos ETEC del período sin persistir

### Categorías
- `GET /api/haberes/core/categoria/` - Listar categorías
- `GET /api/haberes/core/categoria/{categoriaId}` - Obtener categoría
- `GET /api/haberes/core/categoria/puntos` - Listar categorías ETEC con puntos (`puntos > 0`)
- `GET /api/haberes/core/categoria/puntos/{anho}/{mes}` - Listar categorías ETEC con los puntos y el flag de horas vigentes en el período (`categoria_periodo`, fallback a `categoria`)
- `POST /api/haberes/core/categoria/search` - Buscador paginado del catálogo con body `{texto, condiciones, pagina, tamano}`: `texto` busca por nombre (varias palabras con AND) y por ID completo; `condiciones` (`DOCENTE`, `NO_DOCENTE`, `LIQUIDA_POR_HORA`, `ETEC`) se combinan con OR y con el texto con AND; `tamano` va de 1 a 200 (default 50) y responde `{filas, total, pagina, tamano}`
- `POST /api/haberes/core/categoria/upload/{anho}/{mes}` - Cargar categorías desde Excel
- `GET /api/haberes/core/categoria/nogrado` - Listar categorías sin grado

`CategoriaRequest`, `CategoriaResponse`, la planilla de categorías y el upload aceptan `etec_horas`
(1 = la categoría liquida las horas de clase ETEC por la escala de horas) y `puntos` (puntaje del
cargo ETEC); si la planilla no trae la columna `puntos`, la importación conserva el valor existente.
La escala de horas ETEC exige que cada tramo indique una categoría marcada con `etec_horas = 1`.

La generación ETEC resuelve `puntos` y `etec_horas` por período desde `categoria_periodo` y cae en
la plantilla de `categoria` cuando el período no tiene fila o la tiene en cero. En esa lectura, la
fila del período se registra de forma best-effort con los valores heredados (también en
`GET /api/haberes/core/categoria/puntos/{anho}/{mes}`), de modo que el puntaje y el flag queden
versionados en el período.

### ETEC (cargos por puntos y por horas)
- `GET /api/haberes/core/etec/asignacion-cargo/legajo/{legajoId}` - Asignaciones del legajo
- `GET /api/haberes/core/etec/asignacion-cargo/legajo/{legajoId}/periodo/{anho}/{mes}` - Asignaciones vigentes del legajo en el período
- `GET /api/haberes/core/etec/asignacion-cargo/periodo/{anho}/{mes}` - Asignaciones vigentes del período
- `GET /api/haberes/core/etec/asignacion-cargo/categorias` - Categorías con asignaciones
- `GET /api/haberes/core/etec/asignacion-cargo/categoria/{categoriaId}/exists` - Indica si la categoría tiene asignaciones
- `POST /api/haberes/core/etec/asignacion-cargo/` - Alta (`legajoId`, `categoriaId` y `fechaAlta` obligatorios); `horasCargo` y `vecesBasicoMaestroGrado` son opcionales y quedan en `0` si se omiten
- `PUT /api/haberes/core/etec/asignacion-cargo/{asignacionCargoId}` - Actualización (`horasCargo` y `vecesBasicoMaestroGrado` se conservan si el body los omite)
- `DELETE /api/haberes/core/etec/asignacion-cargo/{asignacionCargoId}` - Baja
- `GET /api/haberes/core/etec/control/{anho}/{mes}` - Parámetros ETEC del período (`404` si no está configurado)
- `GET /api/haberes/core/etec/control/{anho}/{mes}/opcional` - Ídem, con `200` y cuerpo vacío si no existe
- `POST /api/haberes/core/etec/control/{anho}/{mes}` - Alta o actualización (upsert) de los parámetros ETEC del período: valor del punto, monto fijo, `horasReferenciaCargo`, `basicoMaestroGrado`, porcentajes de presentismo y adicional carrera, garantía y horas adicionales de carga horaria
- `POST /api/haberes/core/etec/control/copiar/{anho}/{mes}` - Copia los parámetros ETEC y la escala de horas del mes anterior (`409` si el destino existe, `404` si no hay origen)
- `GET /api/haberes/core/etec/hora/escala/{anho}/{mes}` - Escala de horas de clase ETEC del período (lista vacía si el período no tiene control ETEC); cada tramo incluye `categoriaId`
- `PUT /api/haberes/core/etec/hora/escala/{anho}/{mes}` - Reemplazo completo de la escala de horas (valida rangos, solapamientos, importes y que cada tramo indique una categoría marcada como `etec_horas`; la lista vacía borra la escala; `404` si el período no tiene control ETEC)
- `GET /api/haberes/core/etec/arraigo/escala` - Escala global de arraigo ETEC por antigüedad ETEC
- `PUT /api/haberes/core/etec/arraigo/escala` - Reemplazo completo de la escala (valida años mínimos, duplicados y porcentajes entre 0 y 100)
- `GET /api/haberes/core/etec/presentismo/legajo/{legajoId}/periodo/{anho}/{mes}` - Pérdidas de presentismo de los cargos ETEC con puntos del legajo/período
- `GET /api/haberes/core/etec/presentismo/periodo/{anho}/{mes}` - Pérdidas de presentismo del período
- `GET /api/haberes/core/etec/presentismo/asignacion/{asignacionCargoId}/periodo/{anho}/{mes}` - Pérdida de una asignación en el período (`404` si no existe)
- `GET /api/haberes/core/etec/presentismo/{presentismoId}` - Pérdida por id (`404` si no existe)
- `POST /api/haberes/core/etec/presentismo/` - Alta (`asignacionCargoId`, `anho`, `mes` y `motivo` obligatorios; la asignación debe estar vigente y el período abierto; `409` si la pérdida ya existe o el período está acreditado)
- `PUT /api/haberes/core/etec/presentismo/{presentismoId}` - Actualización del motivo (`409` si el período está acreditado)
- `DELETE /api/haberes/core/etec/presentismo/{presentismoId}` - Baja (`409` si el período está acreditado)
- `GET /api/haberes/core/etec/presentismo-horas/legajo/{legajoId}/periodo/{anho}/{mes}` - Estado de la pérdida de presentismo por horas del legajo/período (siempre `200`; `presentismoHorasId` nulo si no hay pérdida y `tieneCargosHoras` indica si el bloque aplica)
- `GET /api/haberes/core/etec/presentismo-horas/periodo/{anho}/{mes}` - Pérdidas de presentismo por horas del período
- `GET /api/haberes/core/etec/presentismo-horas/{presentismoHorasId}` - Pérdida por id
- `POST /api/haberes/core/etec/presentismo-horas/` - Alta (`legajoId`, `anho`, `mes` y `motivo`; el legajo debe tener cursos ETEC de la facultad 15 con horas en el período; `409` si ya existe o el período está acreditado)
- `PUT /api/haberes/core/etec/presentismo-horas/{presentismoHorasId}` - Actualización del motivo (`409` si el período está acreditado)
- `DELETE /api/haberes/core/etec/presentismo-horas/{presentismoHorasId}` - Baja (`409` si el período está acreditado)

La pérdida de presentismo hace que la liquidación ETEC emita el `985 PRESENTISMO ETEC` bruto y el
`984 DESCUENTO PRESENTISMO ETEC` por el mismo importe en negativo. Aplicar
`sql/020_codigo_descuento_presentismo_etec.sql`, `sql/021_etec_presentismo.sql`,
`sql/022_etec_presentismo_horas.sql`, `sql/023_etec_hora_categoria.sql`,
`sql/024_categoria_periodo_etec.sql`, `sql/028_etec_config_horas_basico.sql`,
`sql/029_antiguedad_etec.sql` y `sql/030_adicional_directivo_etec.sql` antes de usar los endpoints.
`sql/023_etec_hora_categoria.sql` agrega `categoria.etec_horas` y `etec_hora_escala.categoria_id`
(con backfill a `2002`), de modo que cada tramo de horas ETEC liquida con la categoría configurada
y ya no con un id fijo en el código. `sql/024_categoria_periodo_etec.sql` agrega
`categoria_periodo.puntos` y `categoria_periodo.etec_horas` con un backfill único desde `categoria`
y debe aplicarse antes de desplegar la generación ETEC por período.

Antes de borrar la liquidación anterior, un legajo con cargos ETEC verifica que el catálogo tenga
los siete códigos ETEC (`984` a `990`); si falta alguno, la liquidación falla y conserva la previa.
En el cálculo, `ConceptosEtecValidator` corta la liquidación cuando un concepto con importe
distinto de cero no tiene código en el catálogo, en lugar de omitirlo en silencio. El catálogo se
valida además al arrancar (`EtecCatalogoValidator`, ver `haberes.etec.catalogo-estricto`).

`sql/028_etec_config_horas_basico.sql` agrega `persona.alta_etec`,
`control_etec.horas_referencia_cargo`, `control_etec.basico_maestro_grado`,
`etec_asignacion_cargo.horas_cargo` y `etec_asignacion_cargo.veces_basico_maestro_grado`, y recrea
`vw_persona_search` para que el buscador devuelva el alta ETEC. Con esos datos:

- el básico del cargo por puntos es `(puntos × valor del punto) × horasCargo / horasReferenciaCargo
  + montoFijoCargo`; sin horas informadas o sin referencia se conserva el importe completo y el
  monto fijo nunca se prorratea;
- la garantía `987 GARANTIA ETEC` de los cargos por puntos se prorratea por
  `horasCargo / horasReferenciaCargo`, y sin esos valores se conserva la garantía completa;
- el `990 ADICIONAL DIRECTIVO ETEC` es `basicoMaestroGrado × vecesBasicoMaestroGrado` del cargo y
  sólo se emite cuando `vecesBasicoMaestroGrado` es mayor a cero.

`sql/029_antiguedad_etec.sql` agrega `antiguedad.meses_etec` con backfill desde `persona.alta_etec`
y recrea `vw_antiguedad_periodo`. El arraigo ETEC y el `989 ARRAIGO ETEC` usan esa antigüedad ETEC en
lugar de la docente; un legajo sin `alta_etec` queda con cero meses ETEC y sin arraigo. El cálculo
también corrige la antigüedad administrativa, que tomaba `alta_docente` como fecha de ingreso.

`sql/030_adicional_directivo_etec.sql` inserta el código `990 ADICIONAL DIRECTIVO ETEC`
(remunerativo, docente, incluido en ETEC y con concepto AFIP `110000` en ambos semestres).

Las cachés `codigos` y `codigos_grupos` usan como clave la versión del catálogo (cantidad de filas,
último `updated` y último id), de modo que una carga por SQL directo invalida la entrada sin
reiniciar el servicio ni depender de los `@CacheEvict`; el TTL de 60 segundos sólo limpia las
claves viejas.

### Control de período
- `GET /api/haberes/core/control/periodo/{anho}/{mes}/opcional` - Control del período (`200` con cuerpo vacío si todavía no existe)
- `POST /api/haberes/core/control/copiar/{anho}/{mes}` - Crear el período copiando el mes anterior (`409` si el destino existe, `404` si no hay origen)

### Seguridad social (Formulario 931)
- `GET /api/haberes/core/seguridadSocial/unique/{anho}/{mes}` - Totales del F.931 del período (`400` si no existe)
- `GET /api/haberes/core/seguridadSocial/unique/{anho}/{mes}/opcional` - Ídem, con `200` y cuerpo vacío si todavía no existe
- `POST /api/haberes/core/seguridadSocial/` - Alta de los totales del período
- `PUT /api/haberes/core/seguridadSocial/{seguridadSocialId}` - Reemplazo total de los totales del período

### Personas
- `GET /api/haberes/core/persona/` - Listar personas
- `GET /api/haberes/core/persona/liquidables` - Listar personas liquidables
- `GET /api/haberes/core/persona/liquidado/{anho}/{mes}` - Personas con liquidación en el período (ordenadas por nombre)
- `POST /api/haberes/core/persona/upload` - Cargar contactos de personas desde Excel

`PersonaResponse` ya no incluye `directivoEtec`: el campo se retiró del contrato JSON junto con la
columna `persona.directivo_etec` (`sql/013_drop_persona_directivo_etec.sql`, idempotente), de modo que
los clientes que todavía lo leían deben dejar de hacerlo. El resto del contrato legacy de persona que
fija `PersonaFacultadLegacyContractTest` se mantiene.

`PersonaRequest`, `PersonaPatchRequest`, `PersonaResponse` y `PersonaSearchResponse` incorporan
`altaEtec` (fecha de alta del legajo en el circuito ETEC, nullable). Se persiste en
`persona.alta_etec` (`sql/028_etec_config_horas_basico.sql`) y alimenta `antiguedad.meses_etec`.

### Usuarios
- `GET /api/haberes/core/usuario/` - Listar usuarios sin hash de clave (`UsuarioResumenResponse`)
- `PUT /api/haberes/core/usuario/{legajoId}/administrador/{administrador}` - Habilitar (`1`) o revocar (`0`) el flag administrador (sólo usuarios sin facultad y sin dejar el sistema sin administradores)

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
- `POST /api/haberes/core/bono/dependencia/{dependenciaId}/{anho}/{mes}/{salida}/lote` - Obtener en una sola lectura el lote completo de bonos de la dependencia (legajos y catálogos compartidos); cada curso del legajo incluye `facultadId` para separar los cursos por facultad en el reporte


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

El servicio tiene RBAC propio en el slice `hexagonal/seguridad`: permisos por módulo, roles,
asignación de roles por usuario, overrides individuales y autenticación con tokens.

- **Fuente de verdad**: `src/main/resources/permisos.yaml` (71 claves en los módulos
  `administrador`, `comun`, `consultas`, `contabilidad`, `designaciones`, `liquidacion`,
  `movimientos`, `novedades` y `seguridad`) y `roles.yaml` (semillas `ADMIN_HABERES`,
  `OPERADOR_NOVEDADES`, `OPERADOR_LIQUIDACION` y `CONSULTA_HABERES`). El sync corre al arrancar
  (`SeguridadManifestSyncRunner`) y también con `POST /permiso/sync`; los permisos declarados se
  crean/actualizan, los que ya no están se retiran con `activo = 0` y la matriz de cada rol se
  reconcilia completa. Una clave exacta inexistente o dejar `ADMIN_HABERES` sin permisos corta
  el sync (y el arranque, si es el sync automático).
- **Permisos efectivos**: roles activos → overrides `usuario_permiso` (`1` otorga, `0` revoca) →
  acceso total del administrador al final (ningún override puede revocárselo). Se cachean en
  `permisosEfectivos` (TTL 60 s, 10.000 entradas) y toda escritura de seguridad invalida la caché,
  de modo que un cambio de rol impacta sin redeploy.
- **Administrador**: el flag `usuario.administrador` es el interruptor y mantiene la membresía de
  `ADMIN_HABERES` en `usuario_rol` (`UsuarioService.setAdministrador`). El rol es protegido: no se
  edita, elimina ni asigna a mano, y no se puede quitar a un usuario administrador mientras tenga
  el flag.
- **PEP**: `RequierePermisoInterceptor` + `RutasPermisos` resuelven el permiso por anotación
  `@RequierePermiso` o por el mapa ruta → claves (prefijo más largo primero, OR entre claves y
  comodín `modulo.*`). Responde `401` sin identidad y `403` con el permiso requerido. Las rutas de
  propio usuario (`usuario/{legajoId}`, `usuario/lastlog/{legajoId}` y
  `permisoEfectivo/usuario/{legajoId}`) sólo admiten el mismo legajo o un administrador. El PEP se
  registra con `app.seguridad.permissions-enforce=true`.
- **Sesión**: `POST /auth/login` emite un token compacto firmado con HMAC-SHA256 (claims `sub`,
  `iat`, `exp` y `jti`; TTL configurable). Con `app.seguridad.token-required=true` todas las rutas
  salvo `actuator`, swagger, `/auth` y `/usuario/isuservalid` exigen `Authorization: Bearer ...`.
  Los permisos y el flag de administrador se resuelven desde la base en cada request (la caché de
  60 s acota el costo), no se congelan en el token. Los tokens service-to-service de
  `HABERES_SERVICE_TOKENS` se reconocen y tienen acceso total.

| Variable | Default | Descripción |
|---|---|---|
| `HABERES_SECURITY_MANIFEST_SYNC` | `true` | Sincronizar `permisos.yaml`/`roles.yaml` al arrancar |
| `HABERES_SECURITY_MANIFEST_DRY_RUN` | `false` | Previsualizar el sync sin escribir |
| `HABERES_SECURITY_TOKEN_REQUIRED` | `false` | Exigir token Bearer en los endpoints no públicos |
| `HABERES_SECURITY_ENFORCE` | `false` | Evaluar permisos por endpoint (PEP) |
| `HABERES_AUTH_SECRET` | *(vacío)* | Secreto HMAC; obligatorio (mínimo 32 bytes) si `token-required=true` |
| `HABERES_AUTH_TTL_HOURS` | `8` | Duración del token de sesión |
| `HABERES_SERVICE_TOKENS` | *(vacío)* | Tokens service-to-service, formato `etec:xxx,report:yyy,tesoreria-core:zzz` |

Despliegue: aplicar `sql/027_rbac_permisos.sql` (idempotente) antes de arrancar con el sync
habilitado; crea las tablas, los roles semilla y reproduce el acceso vigente (usuarios de facultad
→ `OPERADOR_NOVEDADES`, globales no administradores → `OPERADOR_LIQUIDACION`, administradores →
`ADMIN_HABERES`). El despliegue escalonado puede dejar `token-required` y `permissions-enforce` en
`false` (el core nuevo responde al frontend viejo) y activarlos después de configurar
`HABERES_AUTH_SECRET` y los service tokens; el arranque registra un warning por cada control
desactivado.

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
