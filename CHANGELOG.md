## [1.22.1] - 2026-10-29
- fix: `LiquidacionState.items` ahora declara `@Builder.Default` para que el builder de Lombok respete el `HashMap` inicializado del campo; antes Lombok descartaba esa inicialización y un `LiquidacionState.builder()` sin `items` dejaba el mapa en `null`, lo que rompía los accesos de `MakeLiquidacionService` (`state.getItems().computeIfAbsent(...)`, `get`, `containsKey`) al armar un estado sin pasar el mapa. El camino actual de `AfipContextService` sigue inyectando sus propios ítems (basado en `git diff HEAD -- src/main/java/um/haberes/core/service/facade/liquidaciones/LiquidacionState.java`, `MakeLiquidacionService.java` y `AfipContextService.java`)

## [1.22.0] - 2026-10-26
- feat: auditoría de operaciones en `haberes-core` con eventos semánticos (INSERT/UPDATE/DELETE + REQUEST) capturados por listeners de Hibernate, con actor/IP/correlación por header y diff de campos enmascarado (`password`, `cbu`, `cuenta`, `documento`). Los eventos se escriben en `audit_outbox` dentro de la misma transacción del cambio y un sender programado los envía por lote a `haberes-auditoria` (`POST /api/haberes/auditoria/evento/lote`) con reintentos/backoff e idempotencia por `eventId`. Modos `OFF`/`LOG`/`SEND` por `HABERES_AUDIT_MODE`.
- build: DDL del outbox versionado en `sql/001_audit_outbox.sql` (sin Flyway; `ddl-auto: none`).
- test: `AuditDiffBuilderTest` (diff, máscara, truncado) y `AuditOutboxIntegrationTest` (save/update real contra H2, evento en el outbox y password enmascarado).
- refactor: se eliminaron los 32 `@Modifying` de los 19 repositorios JPA (`JpaItemRepository`, `JpaLiquidacionRepository`, `JpaCargoLiquidacionRepository`, `JpaCursoFusionRepository`, `JpaNovedadRepository`, `JpaLetraRepository`, `JpaCargoRepository`, `JpaCursoCargoRepository`, `JpaContactoRepository`, `JpaPersonaFacultadRepository`, `JpaLegajoBancoRepository`, `JpaExcluidoRepository`, `JpaNovedadUploadRepository`, `JpaCategoriaPeriodoRepository`, `JpaCargoClasePeriodoRepository`, `JpaCargoClaseDetalleRepository`, `JpaCursoCargoNovedadRepository`, `JpaLiquidacionAdicionalRepository`, `JpaAdicionalCursoRangoRepository`) para que los deletes derivados carguen entidades, disparen `post-delete` y queden auditados con su `deletedState`; se asume el costo de select + `EntityManager.remove` por entidad (basado en `src/main/java/**/repository/**`)
- fix: nuevo `AuditFlushListener` registrado en `EventType.FLUSH` que vuelca el buffer de auditoría al outbox apenas termina cada flush; cubre los borrados que Hibernate ejecuta recién en el flush final del commit, donde `beforeCommit` ya había drenado el buffer y el evento se perdía (basado en `src/main/java/um/haberes/core/audit/**`)
- test: nuevo `AuditCoverageGuardTest` (guardián de CI sobre `AuditCoverageGuard`) que escanea `src/main/java` y falla si reaparece `@Modifying`, `@Query`, JDBC, SQL nativo, `StatelessSession`, los `deleteAllInBatch`/`deleteAllByIdInBatch` o los `@SQL*`; ignora comentarios y excluye el paquete `audit`; `AuditOutboxIntegrationTest` suma el caso DELETE (3 eventos: INSERT/UPDATE/DELETE, `beforeData.password` enmascarado y `afterData` vacío) (basado en `src/test/java/um/haberes/core/audit/**`)
- build: `bootstrap.yml` configura el batching de Hibernate (`hibernate.jdbc.batch_size: 200`, `order_inserts`, `order_updates`) para agrupar los deletes por entidad; `AGENTS.md` nuevo con la regla de auditoría y su checklist, y README con la subsección "Regla de auditoría" (basado en `src/main/resources/bootstrap.yml`, `AGENTS.md` y `README.md`)
- feat: auditoría de operaciones masivas como resumen (`level=SUMMARY`, `operation=BULK`): nuevo `AuditBulkService`/`AuditBulkScope` que cuenta los cambios de las entidades configuradas en `haberes.audit.summary-entities` (default `ItemEntity,ItemVersionEntity,CargoLiquidacionEntity,CargoLiquidacionVersionEntity`) y emite un evento por entidad y operación con `affectedRows` y `metadata.counts`; el scope se declara en el caso de uso y, fuera de él, la misma entidad sigue emitiendo su `CHANGE` por fila (fail-open); `HABERES_AUDIT_BULK_ENABLED=false` lo desactiva sin deploy (basado en `src/main/java/um/haberes/core/audit/**`)
- refactor: `SaveAllItemsUseCaseImpl`, `DeleteItemsByLegajoUseCaseImpl` y `DeleteZeroItemsByLegajoUseCaseImpl` abren un scope por transacción/legajo; `DeleteItemsByPeriodoUseCaseImpl` deja de ser transaccional y delega en el nuevo `ItemBulkDeleter`, que borra en chunks de 1.000 con `REQUIRES_NEW`, un scope por chunk y `bulkId` compartido; se agrega `ItemRepository.deleteAll(List<Item>)` (basado en `src/main/java/um/haberes/core/hexagonal/liquidaciones/item/**`)
- test: `AuditBulkScopeTest` (agregación por entidad/operación, chunk, idempotencia, disabled), `AuditBulkOutboxIntegrationTest` (un solo `SUMMARY` con conteos en el outbox y `CHANGE` por fila fuera del scope), `ItemBulkDeleterTest` (chunks y scopes) y `SaveAllItemsUseCaseImplTest` (período/legajo homogéneos o mixtos) (basado en `src/test/java/um/haberes/core/**`)
- feat: versionado de `cargo_liquidacion` reactivado: cada generación escribe **siempre** una copia en `cargo_liquidacion_version` (sólo inserts) con `version` = contador por legajo/período (`MAX(version)+1`, empieza en 1); sin flag de configuración. El endpoint legacy `PUT /cargoliquidacion/saveall/{version}` conserva la versión explícita. Como la generación reemplaza el juego completo del legajo/período, el snapshot anterior es el estado previo y permite reconstruir los borrados (basado en `src/main/java/um/haberes/core/hexagonal/liquidaciones/cargo_liquidacion/**` y `service/facade/MakeLiquidacionService.java`)
- feat: `cargo_liquidacion` y `cargo_liquidacion_version` se auditan como `SUMMARY/BULK`: `SaveAllCargoLiquidacionesUseCaseImpl` y los cuatro borrados de cargos (docentes/no docentes por legajo y por período) abren un `AuditBulkScope`, así una generación deja ~3 eventos por legajo en vez de un evento por fila (basado en `src/main/java/um/haberes/core/hexagonal/liquidaciones/cargo_liquidacion/application/usecases/**`)
- build: `sql/002_cargo_liquidacion_version_index.sql` agrega de forma idempotente el índice `(legajo_id, anho, mes, version)` que necesita el cálculo del contador; aplicar a mano antes de desplegar la versión que escribe versiones (MySQL 5.7, sin Flyway) (basado en `sql/002_cargo_liquidacion_version_index.sql`)
- test: `SaveAllCargoLiquidacionesUseCaseImplTest` (contador por legajo/período, versión explícita, lote vacío y scope) (basado en `src/test/java/um/haberes/core/hexagonal/liquidaciones/cargo_liquidacion/**`)
- refactor: se eliminó el flag `HABERES_CARGO_VERSION_ENABLED` (el versionado es incondicional) y se quitó el parámetro `withVersion` del puerto, el servicio y el controller (el endpoint legacy conserva su `version` explícita); `fecha_desde`/`fecha_hasta` quedan mapeadas como `OffsetDateTime` (la tabla volvió a tenerlas como `datetime`) y se eliminan del mapeo las columnas legado `mayor_*`. Se agrega `sql/003_drop_legacy_cargo_version_index.sql` para eliminar el índice viejo `legajo_id`, redundante frente a `idx_cargo_liq_version_legajo_periodo` (basado en `src/main/java/um/haberes/core/model/CargoLiquidacionVersionEntity.java`, `hexagonal/liquidaciones/cargo_liquidacion/**`, `service/facade/MakeLiquidacionService.java` y `sql/`)
- build: la imagen fija la zona horaria de la JVM en UTC (`java -Duser.timezone=UTC -jar ...`) para que los timestamps del proceso (auditoría, outbox y versiones) no dependan de la zona del host (basado en `Dockerfile`)
- docs: nuevo diagrama `docs/diagrams/flujo-auditoria.mmd` (REQUEST/CHANGE/BULK, outbox transaccional, sender y reintentos) registrado en el `diagramsManifest` de `generate-docs.yml` y en el listado del README; `arquitectura-general.mmd` suma el canal de auditoría y `flujo-cargo-liquidacion.mmd` refleja el versionado incondicional (`saveAll(cargos, version)`, `MAX+1` por legajo/período o versión explícita del cliente legacy) (basado en `docs/diagrams/**`, `.github/workflows/generate-docs.yml` y `README.md`)

## [1.21.0] - 2026-10-25
- feat: `POST /api/haberes/core/bono/dependencia/{dependenciaId}/{anho}/{mes}/{salida}/print-prepare` para la impresión masiva de bonos de una dependencia: recorre las liquidaciones del período y por cada legajo verifica integridad, recalcula la actividad (`PrepararBonoUseCase`) y registra la auditoría, con la IP y el solicitante resueltos en el servidor (`ClientIpResolver` + header `X-Legajo-Solicitante`, igual que el bono individual). Un legajo que falla no corta el lote: responde `{ total, preparados, fallidos[{legajoId, motivo}] }`. Reemplaza el `beforePrintBono` que el cliente de escritorio ejecutaba legajo por legajo antes de imprimir (basado en `src/main/java/um/haberes/core/hexagonal/liquidaciones/bono/**`)
- test: `PrepararBonosDependenciaUseCaseImplTest` (lote completo, un fallido que no corta el lote, lote vacío) y `BonoDependenciaControllerTest` (resumen con IP y solicitante resueltos, header inválido → 400) (basado en `src/test/java/um/haberes/core/hexagonal/liquidaciones/bono/**`)
- feat: Endpoints de lectura por período para las planillas de Consultas, todos aditivos y sin `@RequestParam`: `GET /api/haberes/core/persona/liquidado/{anho}/{mes}` (`PersonaService.findAllByLiquidado`, ordenado por nombre), `GET /api/haberes/core/novedadacumulado/codigo/{codigoId}/{anho}/{mes}` (nuevo controller sobre `NovedadAcumuladoService`), `GET /api/haberes/core/legajocursoCantidad/periodo/{anho}/{mes}` (nuevo controller sobre la vista que no tenía endpoint), `GET /api/haberes/core/cargoliquidacion/periodo/{anho}/{mes}` (activos, `findAllActivosByPeriodo`), `GET /api/haberes/core/cargoliquidacion/periodo/{anho}/{mes}/todos` (sin filtro de situación, para básicos), `GET /api/haberes/core/cargoclasedetalle/periodo/{anho}/{mes}` y `GET /api/haberes/core/liquidacion-adicional/periodo/{anho}/{mes}`; las rutas legacy siguen intactas para el cliente de escritorio (basado en `src/main/java/um/haberes/core/**`)
- test: `LegajoCursoCantidadControllerTest` y `NovedadAcumuladoControllerTest` nuevos; escenarios de período en `PersonaControllerTest`, `CargoLiquidacionControllerTest` (activos y todos), `CargoClaseDetalleControllerTest` y `LiquidacionAdicionalControllerTest` (basado en `src/test/java/um/haberes/core/controller/**`)
- feat: Nuevo endpoint de lectura `GET /api/haberes/core/cursofusion/periodo/{anho}/{mes}` que devuelve todas las fusiones del período en una sola llamada (`CursoFusionService.findAllByAnhoAndMes`, ya existente); permite que el reporte de fusión por docente deje de consultar legajo por legajo (cambio aditivo retrocompatible) (basado en `CursoFusionController.java`)
- docs: Nuevo diagrama `docs/diagrams/flujo-bono-dependencia.mmd` con la preparación masiva de bonos por dependencia (lote, IP y solicitante server-side, fallidos que no cortan el lote) y su registro en el `diagramsManifest` de `generate-docs.yml` y en el listado del README; `flujo-bono-individual.mmd` referencia el flujo masivo; `flujo-vistas-reporte.mmd` pasa a 15 controladores de vista y suma `novedadacumulado/codigo` y `legajocursoCantidad/periodo`; `flujo-persona-consultas.mmd` suma `/persona/liquidado/{anho}/{mes}`; `flujo-cargo-liquidacion.mmd` suma `/periodo/{anho}/{mes}` y `/periodo/{anho}/{mes}/todos`; `flujo-cargo-clase.mmd` suma `/cargoclasedetalle/periodo/{anho}/{mes}`; `flujo-liquidacion-adicional.mmd` suma `/periodo/{anho}/{mes}` y `flujo-cursos.mmd` suma `/cursofusion/periodo/{anho}/{mes}` (basado en `git diff HEAD` y en los controladores actuales)

## [1.20.0] - 2026-10-24
- feat: Nuevo endpoint `PATCH /api/haberes/core/persona/{legajoId}` en `PersonaController` para actualización parcial: sólo se modifican los campos informados en `PersonaPatchRequest` (los `null` se ignoran) y en los numéricos anulables (`situacionId`, `dependenciaId`, `obraSocial`, `actividadAfip`, `localidadAfip`, `modeloContratacionAfip`) un `0` borra el valor dejándolo `null`; responde 200 con `PersonaResponse` y 400 vía `PersonaException` si el legajo no existe o el repositorio no pudo actualizar; se agregan el puerto `PartialUpdatePersonaUseCase`, su implementación `PartialUpdatePersonaUseCaseImpl` y el método `PersonaDtoMapper.toDomain(PersonaPatchRequest)` (cambio aditivo: `PUT /persona/` sigue reemplazando la fila completa) (basado en `git diff HEAD` y `src/main/java/um/haberes/core/hexagonal/personas/persona/**`)
- feat: Nuevo endpoint `POST /api/haberes/core/anotador/visar/{anotadorId}` que visa la anotación en una sola operación: recibe `VisarRequest` (`autorizado`, `respuesta`), exige respuesta no vacía (400 `Falta RESPUESTA`), resuelve la IP del revisor en el servidor con `ClientIpResolver` y delega en `AnotadorService.visar(...)`, que persiste `respuesta`, `autorizado`/`rechazado` (mutuamente excluyentes), `visado` e `ipVisado`; responde 204 sin cuerpo y 400 si la anotación no existe (`AnotadorException`) (basado en `git diff HEAD` y `src/main/java/um/haberes/core/controller/AnotadorController.java`)
- feat: Nuevos endpoints de resolución de novedades de cursos en `CursoCargoNovedadController`: `POST /autorizar/{cursoCargoNovedadId}`, `POST /rechazar/{cursoCargoNovedadId}` (ambos con `RespuestaRequest.respuesta` obligatorio) y `POST /anular/{cursoCargoNovedadId}`; el nuevo `CursoCargoNovedadToolService` absorbe en el servidor la cascada que el cliente legacy ejecutaba paso a paso y la corre de forma atómica (`@Transactional`): autorizar cierra todas las novedades pendientes del mismo legajo/curso/período, deja `legajo_control.fusionado = 0`, aplica alta/baja/cambio sobre `curso_cargo` y depura la fusión del legajo; rechazar sólo cierra las pendientes; anular revierte lo autorizado (deshace alta/baja y vuelve a alternar el desarraigo del cambio) o limpia el rechazo; las tres respuestas son 204 sin cuerpo (basado en `git diff HEAD`, `CursoCargoNovedadToolService.java` y `CursoCargoNovedadController.java`)
- feat: Nuevo endpoint de lectura `GET /api/haberes/core/cursocargonovedad/periodo/{anho}/{mes}` que devuelve el padrón completo del período (pendientes + autorizadas + rechazadas), con cada bloque ordenado por apellido, nombre y curso (`CursoCargoNovedadService.findAllByPeriodo`), que reemplaza el reporte que el cliente legacy armaba por partes (cambio aditivo retrocompatible) (basado en `git diff HEAD` y `src/main/java/um/haberes/core/service/CursoCargoNovedadService.java`)
- feat: `DesignacionToolController` suma `POST /fusionar/{legajoId}/{anho}/{mes}` (cuerpo opcional `FusionarRequest.forzar`; `DesignacionToolService.fusionarCompletoByLegajo` resetea `legajo_control.fusionado`, fusiona y recalcula el desarraigo en una transacción), `POST /depurafusion/{legajoId}/{anho}/{mes}` (elimina `curso_fusion` del legajo cuando ya no tiene `curso_cargo` en el período) y `POST /invalidafusion/curso/{cursoId}/{anho}/{mes}` (marca `fusionado = 0` a todos los legajos con cargos del curso); los tres responden 204 y los dos procesos de fusión conservan el corte de períodos previos a 2019-04 (`aplicaExcepcion`) (basado en `git diff HEAD`, `DesignacionToolService.java` y `DesignacionToolController.java`)
- feat: Nuevo `FusionPeriodoController` sobre `/api/haberes/core/designaciontool/periodo` y `FusionPeriodoService` para procesos masivos por lotes: `POST /fusionar/{anho}/{mes}`, `POST /desarraigo/{anho}/{mes}` y `POST /duplicar/{anho}/{mes}` reciben `LoteRequest.legajos` y devuelven `ResumenLote` (`procesados` y `fallidos` con `legajoId`/`error`), procesando cada legajo por separado para que un fallo no corte el lote; `POST /depurar/{anho}/{mes}` elimina las fusiones huérfanas del período y responde 204 (basado en `git diff HEAD`, `FusionPeriodoController.java` y `FusionPeriodoService.java`)
- test: Nuevas pruebas para los endpoints y casos de uso agregados: `PartialUpdatePersonaUseCaseImplTest` (sólo aplica campos informados, interpreta `0` como borrar, falla con legajo inexistente y con update vacío), 2 escenarios PATCH en `PersonaControllerTest`, 4 escenarios del visado en `AnotadorControllerTest` (autorizado, rechazado, respuesta en blanco y anotación inexistente), 5 escenarios de novedades en `CursoCargoNovedadControllerTest` (`periodo`, `autorizar`, `autorizar` con respuesta en blanco, `rechazar` y `anular`), 4 escenarios nuevos de fusión/depuración en `DesignacionToolControllerTest` y la nueva `FusionPeriodoControllerTest` (5 escenarios: los tres lotes, lote vacío y depuración) (basado en `git diff HEAD -- src/test`)
- docs: `flujo-anotacion-aprobacion.mmd` documenta el visado atómico (`POST /anotador/visar`), la resolución server-side de novedades (`autorizar`/`rechazar`/`anular` con `CursoCargoNovedadToolService`) y el padrón del período; `flujo-designacion-tool.mmd` suma la re-fusión completa, la depuración/invalidación de fusión y los procesos masivos por lote de `FusionPeriodoController`; `flujo-persona-consultas.mmd` suma `PATCH /persona/{legajoId}` con `PartialUpdatePersonaUseCase`; no hay diagramas nuevos, por lo que el `diagramsManifest` de `generate-docs.yml` y el listado de diagramas del README no cambian (basado en el código actual de los controladores y en `git diff HEAD`)

## [1.19.2] - 2026-10-23
- chore: Actualización del parent `spring-boot-starter-parent` de 4.1.1 a 4.1.2 y de `spring-cloud.version` de 2025.1.3 a 2025.1.4; además suben Lombok 1.18.38 → 1.18.48, MySQL Connector/J 26.7.0 → 26.10.0, springdoc-openapi 3.1.0 → 3.1.1, commons-lang3 3.20.0 → 3.21.0 y el plugin JaCoCo 0.8.13 → 0.8.15; actualización de build, sin cambios en la API REST (basado en `git diff HEAD -- pom.xml`)
- chore: Actualización de Apache POI y POI-OOXML de 5.5.1 a 6.0.0; el código sólo usa la API básica de `XSSFWorkbook`/`Workbook` en los servicios de planillas (`SheetService`, `ContratadoService`, `LibroSueldoService`, `NovedadFileService`) y los lectores de upload (`ExcelCategoriaUploadFileReader`, `ExcelContactoUploadFileReader`), por lo que los endpoints de generación y lectura de Excel mantienen su contrato (basado en `git diff HEAD -- pom.xml` y `src/main/java`)
- fix: `src/main/resources/bootstrap.yml` agrega `spring.cloud.consul.discovery.query-passing: true` para que Feign/LoadBalancer sólo resuelvan instancias con los checks de Consul en `passing`, evitando enviar tráfico a instancias marcadas como críticas; se mantienen `health-check-path: /actuator/health/readiness` y `health-check-critical-timeout: 5m` (cambio de configuración retrocompatible) (basado en `git diff HEAD -- src/main/resources/bootstrap.yml`)
- fix: `bootstrap.yml` fija explícitamente el contrato de Actuator que consume Consul: `management.endpoint.health.probes.enabled: true`, `management.endpoints.web.exposure.include: health` y `management.endpoint.health.show-details: never`; los tres coinciden con los defaults de Spring Boot 4.1.2, pero quedan blindados en el archivo para que `/actuator/health/readiness` siga existiendo y sea el único endpoint de management publicado, sin exponer detalles internos (basado en `git diff HEAD -- src/main/resources/bootstrap.yml` y la documentación de `management.endpoint.health.*` de Spring Boot)
- test: Nueva clase `ConsulReadinessConfigTest` que carga `bootstrap.yml` con `YamlPropertySourceLoader` y congela los valores de readiness (`health-check-path`, `health-check-critical-timeout`, `query-passing`) y de Actuator (`probes.enabled`, `exposure.include`, `show-details`), de modo que un cambio accidental falle antes de que Consul pode una instancia sana a los cinco minutos (basado en `src/test/java/um/haberes/core/configuration/ConsulReadinessConfigTest.java`)

## [1.19.1] - 2026-10-21
- fix: El registro en Consul (`spring.cloud.consul.discovery`) usa ahora `health-check-path: /actuator/health/readiness` en lugar del health check global, de modo que la instancia se considera sana por su estado de readiness y no por el resultado de las dependencias (base de datos, mail); se agrega `health-check-critical-timeout: 5m` para que Consul pode la instancia cuando el chequeo permanece crítico ese tiempo; el endpoint existe en Spring Boot 4.1.1 porque los probes están habilitados por defecto (`management.endpoint.health.probes.enabled`, default `true`) y el grupo `readiness` sólo evalúa `readinessState`; cambio de configuración retrocompatible, sin impacto en la API REST (basado en `git diff HEAD`, `src/main/resources/bootstrap.yml` y la metadata de `spring-boot-health` 4.1.1)

## [1.19.0] - 2026-10-18
- ci: El job `deploy` de `deploy-staging.yml` pasa a una estrategia `matrix` con `environment: [staging, staging-new]` y `fail-fast: false`, de modo que cada push a la rama `staging` despliega en paralelo sobre ambos runners self-hosted (`runs-on: [self-hosted, linux, "${{ matrix.environment }}"]`) y publica el environment homónimo; el nombre del job pasa a `Deploy staging service (${{ matrix.environment }})` y el script `/data/docker/env-staging/deploy-haberes-core-staging.sh "${GITHUB_SHA}"` se ejecuta una vez por entorno (cambio aditivo, sin impacto en el artefacto ni en la API REST) (basado en `git diff HEAD` y `.github/workflows/deploy-staging.yml`)
- docs: `docs/diagrams/despliegue.mmd` actualizado para describir el pipeline real (GitHub Actions `verify`/`build-image` en `ubuntu-latest`, imagen `vars.DOCKER_IMAGE_NAME` con tag `GITHUB_SHA` en Docker Hub, despliegue en los runners self-hosted de los entornos `develop`, `staging` y `staging-new`, contenedor de la aplicación, MySQL y servicios externos UM) en lugar del esquema genérico Cloud/Kubernetes anterior (basado en `git diff HEAD`, `.github/workflows/deploy-develop.yml` y `.github/workflows/deploy-staging.yml`)

## [1.18.0] - 2026-10-03
- feat: Nuevo endpoint público `PUT /api/haberes/core/usuario/cambiarclave` en `UsuarioController` (fachada `/api/haberes/core/usuario`) para el cambio de clave con verificación server-side de la anterior: valida clave nueva no vacía, coincidencia de `newPassword`/`reClaveNueva` tras `trim()`, clave anterior no vacía, existencia del usuario por `legajoId` y comparación de `sha256Hex(currentPassword.trim())` contra la clave persistida; al éxito persiste `sha256Hex(newPassword.trim())` preservando `lastLog`, `build`, `usuarioId` y `facultadId` en la entidad guardada; cada regla lanza `IllegalArgumentException` con el mensaje de negocio que el controller convierte en 400 (`ProblemDetail` en `detail`, patrón del resto del controller) y el éxito devuelve 204 sin cuerpo; espejo del use case change-password de tesoreria-core sin guarda de admin ni chequeo de clave duplicada, sin tocar `isUserValid` (basado en `git diff HEAD` y `src/main/java/um/haberes/core/service/UsuarioService.java`)
- feat: Nuevo DTO `CambiarClaveRequest` en `model/dto` con `legajoId`, `currentPassword`, `newPassword` y `reClaveNueva`, con `@ToString(exclude = ...)` sobre las tres claves para no exponerlas en logs (basado en `git diff HEAD` y `src/main/java/um/haberes/core/model/dto/CambiarClaveRequest.java`)
- test: Nueva clase `UsuarioServiceTest` con 8 escenarios Mockito/AssertJ: clave nueva vacía, claves no coincidentes, clave anterior vacía, usuario no encontrado, `legajoId` null, clave anterior incorrecta, password persistida vacía (ninguno con `save`) y update exitoso que preserva `lastLog`/`build`/`usuarioId`/`facultadId`; 2 nuevos tests MockMvc en `UsuarioControllerTest` para `PUT /usuario/cambiarclave` (204 sin cuerpo y 400 con mensaje de negocio) (basado en `git diff HEAD -- src/test`)
- docs: Actualizada la secuencia `flujo-administracion-formularios.mmd` con el nuevo endpoint `PUT /usuario/cambiarclave` y su delegación a `UsuarioService.cambiarClave` (no hay diagramas nuevos: el `diagramsManifest` de `generate-docs.yml` y el listado de diagramas del README no cambian) (basado en `git diff HEAD -- src/main/java/um/haberes/core/controller/UsuarioController.java`)

## [1.17.0] - 2026-09-24
- feat: Nuevo endpoint público `GET /contable/imputacion-individual/{legajoId}/{anho}/{mes}` en `ContableController` (fachada `/api/haberes/core/contable`) que devuelve `ImputacionIndividualResponse`: consolida las imputaciones del legajo/período (`legajo_categoria_imputacion`, `legajo_cargo_clase_imputacion`, `legajo_codigo_imputacion`), resuelve nombres de catálogos (categoría, clase de cargo, código, facultad, geográfica y sigla/`acronimo` de dependencia), clasifica los códigos en remunerativo/no remunerativo vía `CodigoGrupoService.findAllByRemunerativo((byte) 1)` y calcula `TotalesImputacionDto` (`totalCargosBasico`, `totalCargosAntiguedad`, `totalClasesBasico`, `totalClasesAntiguedad`, `totalCodigosImporte`, `totalBruto`, `totalNoRemunerativo`); `diferencia` se obtiene de `LegajoContabilidadService.findByUnique` con default `0` cuando no existe registro (captura `LegajoContabilidadException`); nuevos DTOs en `model/dto/imputacion` (`CargoImputacionDto`, `CargoClaseImputacionDto`, `CodigoImputacionDetalleDto`, `TotalesImputacionDto`, `ImputacionIndividualResponse`) y `ContableService` inyecta `CategoriaService`, `CargoClaseService`, `FacultadService`, `GeograficaService` y `DependenciaService` (cambio aditivo retrocompatible) (basado en `git diff HEAD` y `src/main/java/um/haberes/core/service/facade/ContableService.java`)
- test: Nueva clase `ContableServiceImputacionTest` (2 escenarios: cálculo de totales y resolución de catálogos; y tolerancia sin registro contable del legajo con `diferencia=0`) y nuevo test en `ContableControllerTest` para `GET /imputacion-individual` (MockMvc, 200 OK) (basado en `git diff HEAD -- src/test`)
- docs: Actualizada la secuencia `flujo-contable-asiento.mmd` con el nuevo endpoint de lectura `GET /contable/imputacion-individual/{legajoId}/{anho}/{mes}` y su delegación a los servicios de imputación (no hay diagramas nuevos, el `diagramsManifest` de `generate-docs.yml` y el listado del README no cambian) (basado en el código actual de `ContableController.java`)

## [1.16.0] - 2026-09-20
- feat: Nuevo slice hexagonal `liquidaciones/bono` (Bono Individual, migración de `frmImprimirInd.frm` de VB6) con `BonoIndividualController` sobre `/api/haberes/core/bono` y 6 endpoints: `GET /{legajoId}/{anho}/{mes}/integridad` (checleo DEPENDENCIA/LIQUIDACION/ITEM/ACTIVIDAD/LEGAJO_CONTROL en un round-trip), `POST .../prepare` (recalcula y persiste flags docente/otras/clases de `actividad` — beforePrintBono), `POST .../auditoria` y `GET .../auditoria` (alta e historial de `bono_impresion`), `POST .../print-prepare` (orquesta integridad+prepare+auditoría y devuelve 400 con los faltantes si la integridad falla) y `POST .../send-prepare` (valida mail institucional, upsert de `contacto` y auditoría); incluye modelos de dominio, puertos in/out, 11 adaptadores a los servicios legacy, `BonoService`, `BonoException` y DTOs/mapper propios (basado en `git diff HEAD` y `src/main/java/um/haberes/core/hexagonal/liquidaciones/bono/**`)
- feat: Nuevo componente `ClientIpResolver` que resuelve la IP real del cliente detrás de nginx/gateway con precedencia `X-Forwarded-For` (primer valor) → `X-Real-IP` → `getRemoteAddr()`, usado por la auditoría de bono para registrar la IP server-side; el solicitante se resuelve desde el header `X-Legajo-Solicitante` con fallback al body (400 si el header es inválido o falta el solicitante) (basado en `git diff HEAD` y `src/main/java/um/haberes/core/util/ClientIpResolver.java`)
- feat: Nuevos métodos derivados en repositorios/services legacy para soportar el slice bono: `findAllByLegajoIdAndAnhoAndMesOrderByFechaDesc` en `JpaBonoImpresionRepository`/`BonoImpresionService` e `existsByLegajoIdAndAnhoAndMes` en `JpaCargoClaseDetalleRepository`/`CargoClaseDetalleService` (sin `@Query`, cambio aditivo retrocompatible) (basado en `git diff HEAD -- src/main/java/um/haberes/core/repository src/main/java/um/haberes/core/service`)
- test: Nuevas 7 clases de test del slice bono: `PrepararBonoUseCaseImplTest`, `PrintPrepareBonoUseCaseImplTest`, `RegistrarAuditoriaBonoUseCaseImplTest`, `VerificarIntegridadBonoUseCaseImplTest`, `QueryAdaptersTest`, `BonoIndividualControllerTest` (MockMvc, 203 líneas) y `ClientIpResolverTest` (basado en `git diff HEAD -- src/test`)
- docs: Eliminado `docs/bono-individual-endpoints-core.md`, documento de diseño de los endpoints ya implementado en este release; nueva secuencia `flujo-bono-individual.mmd` con el flujo completo E1–E6, registrada en el `diagramsManifest` de `generate-docs.yml` y en el listado de diagramas del README (total 37 diagramas) (basado en `git diff HEAD` y `.github/workflows/generate-docs.yml`)

## [1.15.0] - 2026-09-20
- feat: El dominio `Item` del slice hexagonal `liquidaciones/item` agrega el campo `persona` y `ItemMapper.toDomain()` compone `PersonaMapper` para cargarla; las respuestas que serializan el dominio `Item` (p. ej. `GET /makeLiquidacion/basicoAndAntiguedad/{legajoId}/{anho}/{mes}`) exponen ahora la relación anidada `persona` (cambio aditivo, retrocompatible) (basado en `git diff HEAD` y `src/main/java/um/haberes/core/hexagonal/liquidaciones/item/**`)
- fix: Se corrigió el header HTTP malformado `Cache-ControlEntity` por el header real `Cache-Control` (`no-cache, no-store, must-revalidate`) en la descarga de archivos: `Tool.getHttpEntityForFile()`, `ContratadoController` y las 14 planillas de `SheetController`; todos los exports xlsx/PDF envían ahora el header correcto (basado en `git diff HEAD`)
- test: Reparados 78 tests de controladores MockMvc/Mockito que en `develop` referenciaban clases eliminadas en 1.14.0 (`um.haberes.core.kotlin.model.*`, services y excepciones legacy) y no compilaban; se alinearon a los controllers hexagonales, sus servicios y DTO mappers reales, incluyendo el estado 201 Created de `POST /item/` y el nuevo campo `persona` anidado en las respuestas de `Item` (basado en `git diff HEAD -- src/test`)
- docs: `flujo-item-letra.mmd` anotado con la relación anidada `persona` del dominio `Item` expuesta ahora en las respuestas de makeLiquidacion (basado en `git diff HEAD`)

## [1.14.0] - 2026-09-19
- feat: `CursoCargoResponse` incluye ahora las relaciones anidadas `curso`, `cargoTipo`, `persona`, `designacionTipo` y `categoria`; `CargoLiquidacionResponse` incluye `persona`, `dependencia` y `categoria`; `PersonaResponse` incluye `dependencia`. Los DTO mappers correspondientes (`CursoCargoDtoMapper`, `CargoLiquidacionDtoMapper`, `PersonaDtoMapper`) pasan a componer los mappers de cada slice y `CargoLiquidacion` agrega el campo de dominio `persona` (persistido vía `CargoLiquidacionMapper`/`PersonaMapper`) (basado en `git diff HEAD`)
- refactor: Migración completa de los dominios restantes a arquitectura hexagonal: 25 slices nuevos en 6 contextos acotados (personas: persona/dependencia; cursos: curso/curso_cargo/cargo_tipo/curso_desarraigo/curso_fusion/designacion_tipo; liquidaciones: liquidacion/cargo_liquidacion/item/novedad/codigo/categoria/letra/cargo/acreditacion/acreditacion_pago; contabilidad: 7 slices de imputaciones y legajo_contabilidad), eliminando los services, controladores y repositorios legacy correspondientes y preservando todas las rutas `/api/haberes/core/*` verificadas contra `git show HEAD` (basado en `git diff HEAD`)
- refactor: Eliminación total de Kotlin: ~140 modelos `.kt` migrados a Java (`*Entity.java`, DTOs, views, extern); removidos `kotlin-maven-plugin`, `kotlin-stdlib`, `kotlin-test` y `jackson-module-kotlin` del build (basado en `git diff HEAD` y `pom.xml`)
- refactor: Repositorios legacy renombrados con prefijo `Jpa*` (p. ej. `ActividadRepository`→`JpaActividadRepository`, `PersonaSearchRepositoryCustomImpl`→`JpaPersonaSearchRepositoryCustomImpl`) y agregada interfaz `Jsonifyable` para modelos Java (basado en `git diff HEAD`)
- chore: Eliminado `spring-boot-starter-log4j2` y la exclusión de `spring-boot-starter-logging`, revirtiendo a Logback por defecto de Spring Boot (basado en `git diff HEAD` y `pom.xml`)
- chore: Actualización de Spring Cloud de 2025.1.2 a 2025.1.3 (basado en `git diff HEAD` y `pom.xml`)
- docs: Nuevos diagramas Mermaid que completan los casos de uso sin documentar (30 nuevos, total 36): mapa de contextos acotados (`contextos-hexagonales.mmd`), patrón de slice hexagonal (`arquitectura-slice-hexagonal.mmd`) y secuencias por flujo de negocio: ciclo de liquidación con versionado, cargos de liquidación, items/letras, novedades (gestión y carga masiva Excel), acreditación y orden de pago, cierre contable e imputaciones, catálogos (códigos/categorías/cargos), personas (consultas y upload de contactos), dependencia, cursos, facultad/geográfica, herramientas de designación y cargos de clase, anotación y aprobación docente, ETEC/antigüedad, vistas de reporte, planillas Excel, libro de sueldo/SIJP, planilla de contratados y administración/formularios (basado en el inventario de endpoints y casos de uso verificado en `git diff HEAD`)
- docs: Completados los diagramas de los flujos legacy sin cobertura mediante 5 nuevas secuencias (`flujo-maestros-catalogos.mmd` de nivel/clase/situación/seguridad social/modo de liquidación/lectivo/AFIP/adicional-curso/código-grupo/persona-facultad, `flujo-cargo-clase.mmd` del maestro de cargos de clase por período, `flujo-liquidacion-adicional.mmd` del adicional HCS generado en la liquidación y consumido por cierre contable y planillas, `flujo-actividad-contacto.mmd` de actividad del legajo y contactos, y `flujo-integracion-externa.mmd` de los proxies OpenFeign a `tesoreria-core-service`), verificadas contra los controladores legacy y los `@FeignClient` actuales (basado en `git diff HEAD` y lectura de `src/main/java/um/haberes/core/controller/**`)
- ci: Refactorizado el pipeline de `generate-docs.yml` a un manifiesto único `diagramsManifest` con render dinámico por `textContent` (elimina la duplicación de bloques fetch y la corrupción por `innerHTML`); los 36 diagramas de `docs/diagrams` quedaron registrados y validados uno a uno (basado en los nuevos diagramas)
- docs: Actualizados los diagramas existentes `arquitectura-general.mmd`, `modelo-entidad-relacion.mmd`, `flujo-liquidacion-sueldos.mmd`, `flujo-liquidacion-general.mmd` y `flujo-legajobanco-filtro-codigo.mmd` para reflejar la arquitectura y los flujos reales del código actual (basado en `git diff HEAD`)
- docs: `flujo-cursos.mmd`, `flujo-cargo-liquidacion.mmd` y `flujo-persona-consultas.mmd` anotados con las relaciones anidadas expuestas ahora en `CursoCargoResponse`, `CargoLiquidacionResponse` y `PersonaResponse` (basado en `git diff HEAD`)
- docs: README corregido: se eliminó Kotlin y Log4j2 del stack, Spring Boot actualizado a 4.1.1 (valor real del parent en `pom.xml`, desalineado desde 1.13.0) y ejemplos de endpoints reemplazados por rutas reales verificadas en los controladores

## [1.13.0] - 2026-08-26
- chore: Actualización de MySQL Connector/J de 9.7.0 a 26.7.0 (nuevo esquema de versionado) (basado en `git diff HEAD` y `pom.xml`)
- chore: Actualización de springdoc-openapi de 3.0.3 a 3.1.0 (basado en `git diff HEAD` y `pom.xml`)
- ci: Agregados workflows de despliegue continuo para ramas `develop` y `staging` con verificación Maven, build/push de imagen Docker y deploy automático (basado en `git diff HEAD`)
- chore: Simplificación del ASCII art en `banner.txt` (basado en `git diff HEAD`)

## [1.12.0] - 2026-07-29
- feat: Nuevos endpoints `POST /api/geograficas` (create) y `DELETE /api/geograficas/{id}` en GeograficaController con validación `@Valid`/`@NotBlank` (basado en `git diff HEAD`)
- feat: Nuevos casos de uso `CreateGeograficaUseCase` y `DeleteGeograficaUseCase` con implementaciones en hexagonal (basado en `git diff HEAD`)
- feat: Agregados `@NoArgsConstructor` y `@AllArgsConstructor` a `GeograficaRequest` para compatibilidad con serialización (basado en `git diff HEAD`)
- refactor: `GetFacultadByIdUseCase.getById()` y `FacultadRepository.findById()/update()` cambian retorno a `Optional<T>` para manejo null-safe centralizado (basado en `git diff HEAD`)
- refactor: `FacultadException` migrada de `um.haberes.core.exception` a `um.haberes.core.hexagonal.facultad.application.exception` (basado en `git diff HEAD`)
- refactor: `FacultadResponse` migrada de `@Data` a `@Getter`/`@Setter` y `FacultadDtoMapper.toResponse()` a builder pattern (basado en `git diff HEAD`)
- refactor: `GeograficaService` expone métodos sin `Optional`; las excepciones se manejan internamente con `GeograficaException` (basado en `git diff HEAD`)
- refactor: `GeograficaController` usa `try-catch` + `ResponseStatusException` en lugar de `orElse` con `HttpStatus.NOT_FOUND` (basado en `git diff HEAD`)
- refactor: `DesignacionToolService` simplificado al eliminar manejo de `Optional` en llamada a `getGeograficaById` (basado en `git diff HEAD`)
- chore: Actualización de Kotlin de 2.4.0 a 2.4.10 (basado en `git diff HEAD` y `pom.xml`)
- chore: Eliminado argumento del compilador Kotlin `-Xannotation-default-target=param-property` (basado en `git diff HEAD` y `pom.xml`)
- docs: Corregida sintaxis de nodos con caracteres especiales en diagramas `arquitectura-general.mmd` y `flujo-liquidacion-general.mmd` (basado en validación con mermaid-diagram-generator skill)

## [1.11.1] - 2026-06-19
- chore: Actualización de Spring Boot de 4.0.6 a 4.1.0 (basado en `git diff HEAD` y `pom.xml`)
- chore: Actualización de Kotlin de 2.3.21 a 2.4.0 (basado en `git diff HEAD` y `pom.xml`)
- chore: Actualización de Spring Cloud de 2025.1.0 a 2025.1.3 (basado en `git diff HEAD` y `pom.xml`)
- chore: Actualización de MySQL Connector/J de 9.6.0 a 9.7.0 (basado en `git diff HEAD` y `pom.xml`)
- chore: Actualización de OpenPDF de 3.0.3 a 3.0.5 (basado en `git diff HEAD` y `pom.xml`)
- chore: Actualización de springdoc-openapi de 3.0.2 a 3.0.3 (basado en `git diff HEAD` y `pom.xml`)
- fix: Corregido patrón `@JsonFormat` de `Z` a `XX` en todas las entidades Kotlin con campos `OffsetDateTime` para compatibilidad con Java 25 (basado en `git diff HEAD` en 21 modelos)
- refactor: Unboxing de `Integer`→`int` y `Boolean`→`boolean` en `NovedadFileService` para eliminar auto-boxing innecesario (basado en `git diff HEAD`)

## [1.11.0] - 2026-05-13
- feat: Nuevo endpoint `POST /api/geograficas/ids` en GeograficaController para consulta batch de geográficas por IDs múltiples (basado en análisis de `git diff HEAD`)
- feat: Nuevo use case `GetGeograficasByIdsUseCase` con implementación para búsqueda masiva vía `findAllIn` en GeograficaRepository (basado en análisis de `git diff HEAD`)
- refactor: Renombrados métodos en módulo hexagonal Geografica (`getAll`→`getAllGeograficas`, `getById`→`getGeograficaById`, `update`→`updateGeografica`) para consistencia de API (basado en análisis de `git diff HEAD`)
- refactor: Cambiados tipos de retorno a `Optional<Geografica>` en repositorio, casos de uso, servicio y adaptador de Geografica para manejo null-safe (basado en análisis de `git diff HEAD`)
- refactor: Reemplazados lanzamientos de `GeograficaException` con `Optional.empty()` en `JpaGeograficaRepositoryAdapter.findById` y `update` (basado en análisis de `git diff HEAD`)
- refactor: Reemplazados bloques `JsonMapper` try-catch con `Jsonifier` en `CursoCargoNovedadService` para logging de serialización más limpio (basado en análisis de `git diff HEAD`)
- refactor: Agregado método `jsonify()` al modelo Kotlin `CursoCargoNovedad` (basado en análisis de `git diff HEAD`)
- refactor: Agregada guarda de nulidad para campo `respuesta` en `CursoCargoNovedadService.add()` (basado en análisis de `git diff HEAD`)
- refactor: Actualizado `MakeLiquidacionService.evaluateOnlyETEC` para usar `LiquidacionState` con contexto de cargos clase, corrigiendo evaluación ETEC-only basada en facultad (basado en análisis de `git diff HEAD`)
- refactor: Agregado campo `cargoClases` a `LiquidacionState` y actualizadas referencias en `AfipContextService` (basado en análisis de `git diff HEAD`)
- refactor: Reemplazado `@Autowired` con Lombok `@RequiredArgsConstructor` en `LiquidacionService`, `MakeLiquidacionService`, `DesignacionToolService`, `SheetService`, `AfipContextService` (basado en análisis de `git diff HEAD`)
- refactor: Actualizado `DesignacionToolService` para usar `GeograficaService` hexagonal y `GeograficaMapper` en lugar de modelos Kotlin deprecados (basado en análisis de `git diff HEAD`)
- refactor: Actualizados `ContratadoService` y `SheetService` para usar nuevos nombres de métodos del servicio hexagonal (basado en análisis de `git diff HEAD`)
- refactor: Simplificado `ResponseEntity` en `CursoCargoNovedadController` usando `ResponseEntity.ok()` (basado en análisis de `git diff HEAD`)

## [1.10.1] - 2026-05-12
- refactor: Reemplazo de @Autowired con Lombok @RequiredArgsConstructor en AnotadorController (basado en análisis de git diff HEAD)
- fix: Agregadas verificaciones de nulidad para ipVisado y respuesta en AnotadorService.add() para prevenir NullPointerException (basado en análisis de git diff HEAD)

## [1.10.0] - 2026-05-11
- chore: Actualización de Spring Boot de 4.0.5 a 4.0.6 (basado en análisis de git diff HEAD y pom.xml)
- chore: Actualización de Kotlin de 2.3.20 a 2.3.21 (basado en análisis de git diff HEAD y pom.xml)
- refactor: Migración de Facultad y Geográfica a arquitectura hexagonal con dominio, casos de uso, adaptadores JPA y controladores independientes (basado en análisis de git diff HEAD)
- refactor: Eliminación de modelos Kotlin obsoletos Facultad.kt y Geografica.kt; migración de referencias a FacultadEntity/GeograficaEntity en todas las entidades (basado en análisis de git diff HEAD)
- refactor: Reemplazo de @Autowired con Lombok @RequiredArgsConstructor en FormularioAsignacionCargoService, DesignacionToolService y SheetService (basado en análisis de git diff HEAD)
- refactor: Actualización de imports y llamadas a métodos en ContratadoService, DesignacionToolService y SheetService para usar nuevos servicios hexagonales (basado en análisis de git diff HEAD)

## [1.9.0] - 2026-04-04
- chore: Actualización de Spring Boot de 4.0.2 a 4.0.5 (basado en análisis de git diff HEAD y pom.xml)
- chore: Actualización de Kotlin de 2.3.0 a 2.3.20 (basado en análisis de git diff HEAD y pom.xml)
- chore: Actualización de OpenPDF de 3.0.0 a 3.0.3 (basado en análisis de git diff HEAD y pom.xml)
- chore: Actualización de springdoc-openapi de 3.0.1 a 3.0.2 (basado en análisis de git diff HEAD y pom.xml)
- chore: Agregado de commons-fileupload 1.6.0 en dependencyManagement (basado en análisis de git diff HEAD y pom.xml)
- chore: Actualización de GitHub Actions: actions/checkout@v4→v6, actions/setup-java@v4→v5, actions/cache@v4→v5, actions/upload-pages-artifact@v3→v4, actions/deploy-pages@v4→v5 (basado en análisis de git diff HEAD)
- chore: Actualización de Docker Actions: docker/login-action@v3→v4, docker/metadata-action@v5→v6, docker/setup-buildx-action@v3→v4, docker/build-push-action@v6→v7 (basado en análisis de git diff HEAD)
- chore: Actualización de JDK de 24 a 25 en workflows (basado en análisis de git diff HEAD)
- refactor: Actualización de lombok.version de 1.18.36 a 1.18.38 (basado en análisis de git diff HEAD y pom.xml)

## [1.8.0] - 2026-02-02
- chore: Actualización de Spring Boot de 4.0.1 a 4.0.2 (basado en análisis de git diff HEAD y pom.xml)
- chore: Actualización de MySQL Connector/J de 9.5.0 a 9.6.0 (basado en análisis de git diff HEAD y pom.xml)
- refactor: Mejoras en PersonaSearchRepositoryCustom: cambio de visibilidad de método de public a package-private para encapsulación interna (basado en análisis de git diff HEAD)
- refactor: Reemplazo de constructor explícito con Lombok @RequiredArgsConstructor en PersonaSearchRepositoryCustomImpl (basado en análisis de git diff HEAD)
- refactor: Reemplazo de @Autowired con Lombok @RequiredArgsConstructor en PersonaSearchService (basado en análisis de git diff HEAD)

## [1.7.0] - 2025-10-09
- refactor: Reemplazo de @Autowired con Lombok @RequiredArgsConstructor en ItemController y ItemService (basado en análisis de git diff HEAD)
- refactor: Cambio de visibilidad de métodos en ItemRepository de public a package-private para encapsulación interna (basado en análisis de git diff HEAD)
- feat: Nuevo método deleteAllByLegajoIdAndAnhoAndMesAndImporteAndCodigoIdGreaterThan en ItemRepository y llamado correspondiente en ItemService.deleteAllByZero para eliminar items con importe >0 y codigoId >100 (basado en análisis de git diff HEAD)
- fix: Agregado Objects.requireNonNull en ItemService para prevenir NullPointerException en verificación de incluidoEtec (basado en análisis de git diff HEAD)

## [1.6.0] - 2025-10-08
- feat: Nuevos endpoints en LegajoBancoController para filtrar por código: `findAllSantanderConCodigo` y `findAllOtrosBancosConCodigo` (basado en análisis de git diff HEAD)
- refactor: Mejoras en LegajoBancoService: agregado de métodos `findAllSantanderConCodigo` y `findAllOtrosBancosConCodigo`, uso de `Objects.equals` para verificaciones de nulidad, conversión de `collect(Collectors.toList())` a `toList()`, y logging mejorado con Jsonifier (basado en análisis de git diff HEAD)
- refactor: Reemplazo de `@Autowired` con Lombok `@RequiredArgsConstructor` en LegajoBancoController y LegajoBancoService (basado en análisis de git diff HEAD)
- refactor: Simplificación de ResponseEntity en LegajoBancoController usando `ResponseEntity.ok()` en lugar de `new ResponseEntity<>(..., HttpStatus.OK)` (basado en análisis de git diff HEAD)
- refactor: Agregado de logging en `NovedadService.findAllByCodigo` para mejor trazabilidad (basado en análisis de git diff HEAD)
- docs: Agregado diagrama de secuencia para flujo de LegajoBanco con filtro por código (`docs/diagrams/flujo-legajobanco-filtro-codigo.mmd`) y actualizado pipeline de documentación automática (`.github/workflows/generate-docs.yml`) para incluirlo (basado en cambios en git diff HEAD)

## [1.5.0] - 2025-10-05
- refactor: Mejoras en CursoCargoContratadoClient: cambio de nombres de parámetros para consistencia (basado en git diff HEAD)
- refactor: Reemplazo de @Autowired con Lombok @RequiredArgsConstructor en CursoCargoController y CursoCargoService (basado en git diff HEAD)
- refactor: Simplificación de ResponseEntity en controladores usando ResponseEntity.ok() (basado en git diff HEAD)
- refactor: Agregado de verificaciones de nulidad con Objects.requireNonNull en CursoCargoService (basado en git diff HEAD)
- refactor: Uso de Jsonifier para logging mejorado en CursoCargoService (basado en git diff HEAD)

## [1.4.0] - 2025-10-04
- refactor: Mejoras en controladores CargoTipoController, CursoController y DesignacionTipoController: uso de @RequiredArgsConstructor, simplificación de ResponseEntity y agregado de manejo de excepciones (basado en git diff HEAD)

## [1.3.0] - 2025-10-01
- refactor: Reemplazo de @Autowired con Lombok @RequiredArgsConstructor en CursoCargoContratadoController y CursoCargoContratadoService (basado en análisis de git diff HEAD)
- feat: Actualización de CursoCargoContratadoDto: agregado personaId y documentoId, removido contratadoId (basado en análisis de git diff HEAD)
- feat: Nueva clase utilitaria Jsonifier para serialización JSON en logging (basado en análisis de git diff HEAD)

## [1.2.0] - 2025-09-28
- feat: Migración completa a Consul para descubrimiento de servicios (basado en commits c81019c y c6c440b)
- refactor: Mejoras en controladores y servicios para usar inyección de constructor con @RequiredArgsConstructor y ResponseEntity.ok() (basado en análisis de git diff HEAD)
- refactor: Extracción y centralización de estado y proceso de liquidación en facade/liquidaciones (basado en commits ecc6e59 y da765f5)
- fix: Corrección en manejo de obra social (basado en commits 7fda3f9, 6006214, fad2ec5)
- fix: Corrección en inasistencias (basado en commit 48bc981)
- fix: Corrección en directorio de upload (basado en commits f2acc4e y c9b4988)
- chore: Actualización de versiones de dependencias en README.md y agregado de firma_new.png en Dockerfile (basado en git diff HEAD)
- docs: Agregado diagrama de flujo de liquidación general masiva en documentación automática (basado en cambios en .github/workflows/generate-docs.yml)
- refactor: Cambio en ContratadoPersonaDto de contratadoId a uniqueId (basado en git diff HEAD)

## [1.0.0] - 2025-07-19
## [1.1.0] - 2025-07-31
- feat: Implementación de liquidación general masiva con seguimiento de progreso y endpoints REST dedicados
- feat: Nuevos servicios y controladores para procesos de liquidación asíncrona (`MakeLiquidacionGeneralController`, `MakeLiquidacionGeneralService`, `LiquidacionProceso`, `LiquidacionProcesoService`, `LiquidacionProcessor`)
- refactor: Profundización en la inmutabilidad y manejo de estado en `MakeLiquidacionService`
- chore: Eliminación de `logback-spring.xml` y migración definitiva a Log4j2
- chore: Mejoras en la configuración de caché y soporte para Consul en `bootstrap.yml`
- test: Nuevos tests de integración y unitarios para liquidación general y controladores
- docs: Actualización de diagramas y documentación para reflejar la nueva arquitectura de liquidación masiva

- refactor: Eliminación de prefijos 'I' en repositorios y migración de clases a `um.haberes.core`
Todos los cambios notables en este proyecto serán documentados en este archivo.

El formato está basado en [Keep a Changelog](https://keepachangelog.com/es-ES/1.0.0/),
y este proyecto adhiere a [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [0.0.1-SNAPSHOT] - 2024-07-29

### Agregado
- Envío del reporte de cursos de docentes al microservicio de reportes (2024-07-28, `b8b85ec`).
- Logs al servicio `CursoCargoNovedadService` para mejor trazabilidad (2024-07-27, `330cc16`).
- Campo `build` a la entidad de usuario (2024-07-22, `cbbb62a`).
- Pruebas para el sistema de caché con Caffeine (2024-07-13, `b540604`).
- Módulo para secretarios (2024-06-17, `04a7f9e`).
- Liquidación de adicional por carga horaria para la carrera de corredor en Rio 4 (2024-05-30, `75cf15d`).
- Generación de la planilla de contratados (2024-05-26, `f5e3b69`).
- Chequeo de inasistencias de ETEC para bono de sueldo (2024-05-02, `79965b8`).
- Campo `Porcentaje Imputado` a la vista de `Importado Aporte` (2024-04-15, `77fece0`).
- Documentación de API con Swagger (2023-01-11, `d41f393`).
- Integración con CI a través de GitHub Actions (`maven.yml`) (2022-12-26, `1e32656`).

### Cambiado
- Actualización de versiones de dependencias y migración a Eureka (2024-07-21, `cc6b7b0`).
- Integración con API Gateway (2024-07-19, `f12ea3a`).
- Actualización de versiones de Spring Boot y Spring Cloud (2024-07-06, `5493c36`).
- Migración del proyecto a `um.haberes.core-service` (2024-04-14, `e762414`).
- Actualizaciones de versión de Spring Boot (2024-02-27, `5b0ce7f`), Java a 21 (2024-01-03, `371b789`) y otras dependencias.
- Migración de la mayoría de los modelos de dominio de Java a Kotlin (2023-10-30, `b5e8ced`).
- Refactorización de la lógica de liquidación por designación (2023-09-25, `8756c77`).

### Corregido
- Solucionado error en la baja de novedades (2024-07-29, `4bd5e71`).
- URLs de varios endpoints tras la migración a gateway (2024-07-22, `5e09c92`).
- Corrección en el DTO de Contratados (2024-06-08, `51208b2`).

### Eliminado
- Funcionalidad de envío de bono (`Send Bono`) (2024-07-22, `8f2c4ae`).
- Integración con Spring Cloud Config Server (2024-07-13, `ac23c54`).
- Uso de Crystal Reports en el módulo de novedades (2024-06-19, `1044a7f`).

---

**Nota**: Este changelog está basado únicamente en información verificable del historial de git y el código actual. Las fechas y hashes de commit corresponden al historial real del repositorio.