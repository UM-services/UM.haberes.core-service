## [1.0.0] - 2025-07-19
- refactor: Eliminación de prefijos 'I' en repositorios y migración de clases a `um.haberes.core`
- feat: Implementación de adicional por hora para cargo clase en liquidaciones
- feat: Nuevos servicios de vista: TotalItemService, TotalNovedadService, TotalMensualService, TotalSalidaService
- feat: Nuevos tests de integración y unitarios para servicios y controladores
- docs: Actualización y creación de diagramas Mermaid (arquitectura, flujo de liquidación, ERD, despliegue)
- docs: Actualización del workflow de documentación automática y del portal de documentación
- fix: Mejoras en logs y manejo de errores en servicios de liquidación
- chore: Actualización de nombres de paquetes y limpieza de código legacy
# Changelog

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