# UM.haberes.core-service

## Estado del CI

[![UM.tesoreria.haberes-service CI](https://github.com/UM-services/UM.haberes.core-service/actions/workflows/maven.yml/badge.svg)](https://github.com/UM-services/UM.haberes.core-service/actions/workflows/maven.yml)

## Descripción

Servicio core para la gestión de haberes de la Universidad de Mendoza. Este microservicio forma parte de la arquitectura de Tesorería y se encarga de la gestión, cálculo y procesamiento de haberes para el personal. El servicio está diseñado para manejar liquidaciones, acreditaciones, legajos y toda la lógica relacionada con el procesamiento de haberes.

## Características Principales

- Gestión completa del ciclo de haberes
- Cálculo automático de liquidaciones
- Integración con otros servicios de Tesorería
- Generación de reportes en múltiples formatos (PDF, Excel)
- Caché implementado con Caffeine para optimizar rendimiento
- Documentación automática con OpenAPI/Swagger
- Gestión de legajos y personas
- Procesamiento de acreditaciones
- Integración con AFIP
- Manejo de facultades y dependencias
- Sistema de auditoría automática
- Soporte para múltiples formatos de salida

## Tecnologías

- Java 21
- Spring Boot 3.4.4
- Spring Cloud 2024.0.1
- Kotlin 2.1.20
- MySQL
- Maven
- Log4j2 para logging
- Caffeine para caché
- Apache POI para Excel
- LibrePDF para PDF
- SpringDoc OpenAPI 2.8.6
- Eureka para service discovery
- Feign para clientes HTTP

## Documentación

- [Documentación de la API](https://um-services.github.io/UM.haberes.core-service)
- [Wiki del Proyecto](https://github.com/UM-services/UM.haberes.core-service/wiki)
- [Documentación Técnica](https://um-services.github.io/UM.haberes.core-service/project-documentation)

## Configuración del Proyecto

### Requisitos Previos

- JDK 21
- Maven 3.8.8+
- MySQL 8+
- Docker (opcional, para contenedorización)

### Instalación

```bash
git clone https://github.com/UM-services/UM.haberes.core-service.git
cd UM.haberes.core-service
mvn clean install
```

### Ejecución

```bash
mvn spring-boot:run
```

### Docker

```bash
# Construir la imagen
docker build -t um-haberes-service .

# Ejecutar el contenedor
docker run -p 8093:8093 um-haberes-service
```

## Endpoints Principales

El servicio expone sus endpoints a través de Swagger UI:
- Local: http://localhost:8080/swagger-ui.html
- Producción: https://api.tesoreria.um.edu.ar/haberes/swagger-ui.html

### Principales Endpoints

- `/api/haberes/core/acreditacionpago` - Gestión de acreditaciones de pago
- `/api/haberes/core/legajobanco` - Gestión de legajos bancarios
- `/api/haberes/core/persona` - Gestión de personas
- `/api/haberes/core/sheet` - Generación de hojas de cálculo

### Configuración

El servicio se configura a través de:
- `bootstrap.yml` - Configuración de Spring Cloud
- `application.yml` - Configuración de la aplicación
- Variables de entorno para valores sensibles

### Logging

El servicio utiliza Log4j2 con:
- Rotación diaria de logs
- Tamaño máximo de archivo: 10MB
- Niveles de log configurables por paquete

### Caché

Implementado con Caffeine:
- Tiempo de expiración: 60 minutos
- Tamaño máximo: 100 entradas
- Optimizado para consultas frecuentes

## Integración Continua

El proyecto utiliza GitHub Actions para:
- Compilación y pruebas automáticas
- Generación de documentación
- Despliegue automático
- Actualización de la wiki
- Construcción de imágenes Docker
- Pruebas de integración

## Arquitectura

El servicio está estructurado en:
- Controladores REST
- Servicios de negocio
- Repositorios JPA
- Modelos en Kotlin
- Clientes Feign para integración
- Configuración centralizada

## Seguridad

- Integración con Eureka para service discovery
- Validación de datos con Spring Validation
- Manejo seguro de credenciales
- Logs auditables

## Monitoreo

- Endpoints de Actuator
- Métricas de caché
- Estado de la aplicación
- Health checks

## Contribución

1. Fork del repositorio
2. Crear una rama para la feature (`git checkout -b feature/nueva-caracteristica`)
3. Commit de los cambios (`git commit -am 'Agrega nueva característica'`)
4. Push a la rama (`git push origin feature/nueva-caracteristica`)
5. Crear un Pull Request

## Licencia

Este proyecto es propiedad de la Universidad de Mendoza. Todos los derechos reservados.

## Contacto

- Equipo de Desarrollo de Tesorería UM
- Email: desarrollo.tesoreria@um.edu.ar
