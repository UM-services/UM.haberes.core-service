-- 027 — RBAC de Haberes: catálogo de permisos, roles, asignaciones y overrides.
--
-- Base: `haberes` (MySQL 5.7.43). Se aplica EXTERNAMENTE antes de desplegar
-- haberes-core con el manifiesto de seguridad habilitado (ddl-auto: none).
--
-- Idempotente: se puede ejecutar N veces. Crea el esquema y los roles semilla, y
-- asigna los roles que reproducen el acceso vigente. El catálogo de permisos NO se
-- inserta acá: lo sincroniza haberes-core desde `src/main/resources/permisos.yaml`
-- al arrancar, y después reconcilia la matriz de cada rol desde `roles.yaml`.
--
-- Rollback (en este orden, por las FKs):
--   DELETE FROM usuario_permiso;
--   DELETE FROM usuario_rol;
--   DELETE FROM rol_permiso;
--   DROP TABLE rol_permiso;
--   DROP TABLE usuario_rol;
--   DROP TABLE usuario_permiso;
--   DROP TABLE rol;
--   DROP TABLE permiso;

CREATE TABLE IF NOT EXISTS `permiso` (
    `permiso_id`  BIGINT       NOT NULL AUTO_INCREMENT,
    `clave`       VARCHAR(120) NOT NULL,
    `descripcion` VARCHAR(255) NOT NULL,
    `modulo`      VARCHAR(60)  NOT NULL,
    `aplicacion`  VARCHAR(20)  NOT NULL DEFAULT 'HABERES',
    `activo`      TINYINT      NOT NULL DEFAULT 1,
    `created`     DATETIME(6)  NOT NULL,
    `updated`     DATETIME(6)  NOT NULL,
    PRIMARY KEY (`permiso_id`),
    UNIQUE KEY `uq_permiso_clave` (`aplicacion`, `clave`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `rol` (
    `rol_id`      BIGINT       NOT NULL AUTO_INCREMENT,
    `nombre`      VARCHAR(80)  NOT NULL,
    `descripcion` VARCHAR(255) DEFAULT NULL,
    `aplicacion`  VARCHAR(20)  NOT NULL DEFAULT 'HABERES',
    `activo`      TINYINT      NOT NULL DEFAULT 1,
    `created`     DATETIME(6)  NOT NULL,
    `updated`     DATETIME(6)  NOT NULL,
    PRIMARY KEY (`rol_id`),
    UNIQUE KEY `uq_rol_nombre` (`aplicacion`, `nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `rol_permiso` (
    `rol_permiso_id` BIGINT      NOT NULL AUTO_INCREMENT,
    `rol_id`         BIGINT      NOT NULL,
    `permiso_id`     BIGINT      NOT NULL,
    `created`        DATETIME(6) NOT NULL,
    `updated`        DATETIME(6) NOT NULL,
    PRIMARY KEY (`rol_permiso_id`),
    UNIQUE KEY `uq_rol_permiso` (`rol_id`, `permiso_id`),
    CONSTRAINT `fk_rp_rol`     FOREIGN KEY (`rol_id`)     REFERENCES `rol` (`rol_id`),
    CONSTRAINT `fk_rp_permiso` FOREIGN KEY (`permiso_id`) REFERENCES `permiso` (`permiso_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `usuario_rol` (
    `usuario_rol_id` BIGINT      NOT NULL AUTO_INCREMENT,
    `legajo_id`      INT         NOT NULL,
    `rol_id`         BIGINT      NOT NULL,
    `created`        DATETIME(6) NOT NULL,
    `updated`        DATETIME(6) NOT NULL,
    PRIMARY KEY (`usuario_rol_id`),
    UNIQUE KEY `uq_usuario_rol` (`legajo_id`, `rol_id`),
    CONSTRAINT `fk_ur_usuario` FOREIGN KEY (`legajo_id`) REFERENCES `usuario` (`legajo_id`),
    CONSTRAINT `fk_ur_rol`     FOREIGN KEY (`rol_id`)     REFERENCES `rol` (`rol_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `usuario_permiso` (
    `usuario_permiso_id` BIGINT      NOT NULL AUTO_INCREMENT,
    `legajo_id`          INT         NOT NULL,
    `permiso_id`         BIGINT      NOT NULL,
    `otorgado`           TINYINT     NOT NULL,
    `created`            DATETIME(6) NOT NULL,
    `updated`            DATETIME(6) NOT NULL,
    PRIMARY KEY (`usuario_permiso_id`),
    UNIQUE KEY `uq_usuario_permiso` (`legajo_id`, `permiso_id`),
    CONSTRAINT `fk_up_usuario` FOREIGN KEY (`legajo_id`) REFERENCES `usuario` (`legajo_id`),
    CONSTRAINT `fk_up_permiso` FOREIGN KEY (`permiso_id`) REFERENCES `permiso` (`permiso_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------------
-- Roles semilla (la matriz rol_permiso la reconcilia el arranque desde roles.yaml)
-- ---------------------------------------------------------------------------
INSERT INTO `rol` (`nombre`, `descripcion`, `aplicacion`, `activo`, `created`, `updated`)
SELECT 'ADMIN_HABERES',
       'Administración completa de Haberes: todos los permisos, incluidos roles y permisos',
       'HABERES', 1, NOW(6), NOW(6)
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM `rol` WHERE `aplicacion` = 'HABERES' AND `nombre` = 'ADMIN_HABERES');

INSERT INTO `rol` (`nombre`, `descripcion`, `aplicacion`, `activo`, `created`, `updated`)
SELECT 'OPERADOR_NOVEDADES', 'Operación completa del módulo Novedades', 'HABERES', 1, NOW(6), NOW(6)
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM `rol` WHERE `aplicacion` = 'HABERES' AND `nombre` = 'OPERADOR_NOVEDADES');

INSERT INTO `rol` (`nombre`, `descripcion`, `aplicacion`, `activo`, `created`, `updated`)
SELECT 'OPERADOR_LIQUIDACION',
       'Operación de liquidación, designaciones, consultas, movimientos y contabilidad',
       'HABERES', 1, NOW(6), NOW(6)
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM `rol` WHERE `aplicacion` = 'HABERES' AND `nombre` = 'OPERADOR_LIQUIDACION');

INSERT INTO `rol` (`nombre`, `descripcion`, `aplicacion`, `activo`, `created`, `updated`)
SELECT 'CONSULTA_HABERES', 'Consulta de liquidaciones y reportes, sin operación', 'HABERES', 1, NOW(6), NOW(6)
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM `rol` WHERE `aplicacion` = 'HABERES' AND `nombre` = 'CONSULTA_HABERES');

-- ---------------------------------------------------------------------------
-- Migración del acceso vigente
-- ---------------------------------------------------------------------------

-- Usuarios de facultad → Novedades.
INSERT INTO `usuario_rol` (`legajo_id`, `rol_id`, `created`, `updated`)
SELECT u.`legajo_id`, r.`rol_id`, NOW(6), NOW(6)
FROM `usuario` u
JOIN `rol` r ON r.`aplicacion` = 'HABERES' AND r.`nombre` = 'OPERADOR_NOVEDADES'
WHERE u.`facultad_id` IS NOT NULL
ON DUPLICATE KEY UPDATE `updated` = VALUES(`updated`);

-- Usuarios globales no administradores → Liquidación.
INSERT INTO `usuario_rol` (`legajo_id`, `rol_id`, `created`, `updated`)
SELECT u.`legajo_id`, r.`rol_id`, NOW(6), NOW(6)
FROM `usuario` u
JOIN `rol` r ON r.`aplicacion` = 'HABERES' AND r.`nombre` = 'OPERADOR_LIQUIDACION'
WHERE u.`facultad_id` IS NULL AND u.`administrador` = 0
ON DUPLICATE KEY UPDATE `updated` = VALUES(`updated`);

-- Administradores → ADMIN_HABERES (el flag también lo garantiza en runtime).
INSERT INTO `usuario_rol` (`legajo_id`, `rol_id`, `created`, `updated`)
SELECT u.`legajo_id`, r.`rol_id`, NOW(6), NOW(6)
FROM `usuario` u
JOIN `rol` r ON r.`aplicacion` = 'HABERES' AND r.`nombre` = 'ADMIN_HABERES'
WHERE u.`administrador` = 1
ON DUPLICATE KEY UPDATE `updated` = VALUES(`updated`);
