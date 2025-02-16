# UM.haberes.core-service

## Estado del CI

[![UM.tesoreria.haberes-service CI](https://github.com/UM-services/UM.haberes.core-service/actions/workflows/maven.yml/badge.svg)](https://github.com/UM-services/UM.haberes.core-service/actions/workflows/maven.yml)

## Descripción

Servicio core para la gestión de haberes de la Universidad de Mendoza. Este microservicio forma parte de la arquitectura de Tesorería y se encarga de la gestión, cálculo y procesamiento de haberes para el personal.

## Características Principales

- Gestión completa del ciclo de haberes
- Cálculo automático de liquidaciones
- Integración con otros servicios de Tesorería
- Generación de reportes en múltiples formatos (PDF, Excel)
- Caché implementado con Caffeine para optimizar rendimiento
- Documentación automática con OpenAPI/Swagger

## Tecnologías

- Java 21
- Spring Boot 3.4.2
- Spring Cloud 2024.0.0
- Kotlin 2.1.10
- MySQL
- Maven

## Documentación

- [Documentación de la API](https://um-services.github.io/UM.haberes.core-service)
- [Wiki del Proyecto](https://github.com/UM-services/UM.haberes.core-service/wiki)
- [Documentación Técnica](https://um-services.github.io/UM.haberes.core-service/project-documentation)

## Configuración del Proyecto

### Requisitos Previos

- JDK 21
- Maven 3.8.8+
- MySQL 8+

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

## Endpoints Principales

El servicio expone sus endpoints a través de Swagger UI:
- Local: http://localhost:8080/swagger-ui.html
- Producción: https://api.tesoreria.um.edu.ar/haberes/swagger-ui.html

## Integración Continua

El proyecto utiliza GitHub Actions para:
- Compilación y pruebas automáticas
- Generación de documentación
- Despliegue automático
- Actualización de la wiki

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
