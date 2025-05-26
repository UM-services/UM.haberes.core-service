# Changelog

Todos los cambios notables en este proyecto serán documentados en este archivo.

El formato está basado en [Keep a Changelog](https://keepachangelog.com/es-ES/1.0.0/),
y este proyecto adhiere a [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [0.1.0] - 2024-03-25

### Agregado
- Implementación de DocenteDesignacion para manejo de designaciones docentes
- Nuevo campo `estadoDocente` en modelos de CargoLiquidacion y Categoria
- Mejoras en la generación de reportes con Apache POI
- Integración con Jackson para mejor serialización JSON
- Logging mejorado con mensajes más descriptivos

### Cambiado
- Actualización de Spring Boot a 3.4.4
- Actualización de Spring Cloud a 2024.0.1
- Actualización de SpringDoc OpenAPI
- Optimización de código con mejores prácticas de Java/Kotlin
- Mejora en el manejo de excepciones y errores

### Corregido
- Manejo de nulos en operaciones con objetos
- Mejora en la validación de datos
- Corrección en el cálculo de liquidaciones
- Optimización de consultas a base de datos

## [0.0.1] - 2024-02-15

### Agregado
- Estructura inicial del proyecto
- Configuración básica de Spring Boot
- Integración con Spring Cloud
- Sistema de caché con Caffeine
- Generación de reportes
- Manejo de excepciones básico
- Documentación inicial de API con OpenAPI

### Cambiado
- Actualización de dependencias base
- Mejora en la estructura del proyecto

### Corregido
- Ajustes en la configuración de seguridad
- Correcciones menores en el manejo de datos 