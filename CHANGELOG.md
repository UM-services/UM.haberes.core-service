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