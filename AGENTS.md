# AGENTS.md — haberes-core

Reglas para trabajar en `UM.haberes.core-service`. Contexto de diseño de la auditoría en
`PLANIFICACION_AUDITORIA_HABERES.md` (raíz del workspace).

## Regla de auditoría (obligatoria)

Toda escritura de negocio debe pasar por el ciclo de vida de una entidad JPA (`save`,
`delete`, `deleteById`, `deleteAll` con entidades o un derived delete **sin** `@Modifying`).
Está prohibido cualquier anotación o API que ejecute DML sin disparar
`PostInsert`/`PostUpdate`/`PostDelete`:

- `@Modifying`
- `@Query` (ya prohibido globalmente)
- `JdbcTemplate` / `NamedParameterJdbcTemplate`
- `EntityManager.createNativeQuery(...)` / `createMutationQuery(...)`
- `StatelessSession`
- `deleteAllInBatch` / `deleteAllByIdInBatch`
- `@SQLDelete` / `@SQLInsert` / `@SQLUpdate`
- `executeUpdate()` sobre JPQL/nativo

**Única excepción:** el paquete `um.haberes.core.audit` (canal de auditoría: escribe el
outbox con JDBC) y los scripts DDL de `sql/`. No es una excepción para datos de negocio.

**Lecturas:** permitidas con derived queries, Criteria o `EntityManager`; la regla aplica a
escrituras.

**Si un borrado masivo es inevitable:** hacerlo por chunks con entidades y transacciones
acotadas (para no romper memoria), nunca con SQL que saltee los listeners. Si el volumen
justifica un resumen en vez de N eventos, se implementa un evento `BULK` explícito, no un
bypass.

**Verificación:** `AuditCoverageGuardTest` falla si aparece cualquiera de esos patrones.
Antes de un PR: `mvn test` y `grep -rn "@Modifying\|@Query" src/main/java | grep -v "/audit/"`
(debe dar 0; el paquete `audit` se excluye porque contiene los patrones del guard como
strings).

## Checklist de code review

- [ ] La escritura nueva usa entidades JPA.
- [ ] No hay `@Modifying`, `@Query`, JDBC ni SQL nativo en el paquete de negocio.
- [ ] `AuditCoverageGuardTest` verde.
- [ ] Si el volumen es alto, el servicio hace chunks (y el evento queda auditado).
- [ ] Si la operación es masiva, abre un `AuditBulkScope` (no genera un evento por fila).

## Operaciones masivas (`SUMMARY`/`BULK`)

Los recálculos que escriben o borran miles de filas se auditan como un resumen, no fila por
fila. El caso de uso declara el scope dentro de su transacción:

```java
try (AuditBulkScope ignored = auditBulk.begin(
        BulkContext.of("recalculo-legajo", anho, mes, legajoId))) {
    itemRepository.deleteAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
    itemRepository.saveAll(items);
}
```

Reglas:

- El scope se abre **en el caso de uso**, nunca en un repositorio: el repo sólo ve un filtro.
- Sólo se resumen las entidades de `haberes.audit.summary-entities` (hoy `ItemEntity`,
  `ItemVersionEntity`, `CargoLiquidacionEntity` y `CargoLiquidacionVersionEntity`); el resto
  de las escrituras de la misma transacción sigue emitiendo su `CHANGE` normal.
- Fuera del scope, la misma entidad emite `CHANGE` por fila. La configuración no silencia
  nada: **fail-open hacia el detalle**.
- Al cerrar se hace `flush` y se emite un evento por entidad y operación
  (`level=SUMMARY`, `operation=BULK`) con `affectedRows` y `metadata.counts`. No popear el
  scope antes del flush: los `Post*` pendientes se contarían dos veces (resumen + filas).
- Para borrados de período usar `ItemBulkDeleter`: chunks de 1.000 con transacción propia y
  un scope por chunk, todos con el mismo `bulkId`.
- `HABERES_AUDIT_BULK_ENABLED=false` desactiva el resumen sin deploy (vuelve a fila por fila).

## Versionado de `cargo_liquidacion`

Cada generación de cargos (docentes/no docentes) deja **siempre** una copia en
`cargo_liquidacion_version` (sólo inserts, nunca updates). No hay flag de configuración: el
registro es parte del flujo de generación.

- `version` es un **contador por legajo/período**: `MAX(version)+1`, empezando en 1. El
  endpoint legacy `PUT /cargoliquidacion/saveall/{version}` conserva la versión explícita que
  recibe.
- La generación es un **reemplazo completo** del legajo/período (borra y reinserta). Por eso el
  snapshot N-1 es el estado previo a la generación N y la cadena de snapshots permite
  reconstruir los borrados que resume la auditoría.
- Antes de desplegar una versión que escribe versiones, aplicar
  `sql/002_cargo_liquidacion_version_index.sql`: sin el índice, `MAX(version)` escanea la tabla
  histórica (1M+ filas).
- La escritura va en la misma transacción que los cargos: si falla, falla la generación del
  legajo.
- La tabla es append-only y crece por generación: definir retención antes de producción.
- Límite conocido: las ediciones manuales de `cargo_liquidacion` (create/update/delete fuera de
  la generación) no quedan en la tabla de versiones; la reconstrucción asume que todo cambio
  pasa por la generación.
- Esquema real verificado el 2026-10-26 (MySQL 5.7.43, `STRICT_TRANS_TABLES`):
  `cargo_liquidacion_version` tiene `fecha_desde`/`fecha_hasta` como `datetime` (se
  reincorporaron el 2026-10-26 y la entidad las mapea como `OffsetDateTime`), sin las columnas
  legado `mayor_*`. Insert real probado con rollback. El índice
  `idx_cargo_liq_version_legajo_periodo (legajo_id, anho, mes, version)` está aplicado y el
  viejo `legajo_id (legajo_id, anho, mes)` se eliminó por redundante
  (`sql/003_drop_legacy_cargo_version_index.sql`).

## Notas de implementación

- Los derived deletes sin `@Modifying` hacen select + `EntityManager.remove` por entidad:
  es el costo asumido para que cada borrado quede auditado. No "optimizar" con
  `deleteAllInBatch` ni volviendo a `@Modifying`.
- El batching de Hibernate (`spring.jpa.properties.hibernate.jdbc.batch_size: 200`,
  `order_inserts`, `order_updates`) agrupa los `DELETE` por entidad.
- La captura corre en los listeners `post-insert`/`post-update`/`post-delete` y en un
  listener de `flush`: los deletes se ejecutan recién en el flush final, así que el volcado
  al outbox no puede depender sólo de `beforeCommit`.
