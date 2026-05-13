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