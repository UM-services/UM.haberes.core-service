-- Configuración ETEC: alta ETEC de la persona y parámetros de horas / básico de maestro de grado.
--
-- 1) persona.alta_etec
--    Misma semántica y tipo que alta_docente / alta_administrativa: fecha de alta del
--    legajo en el circuito ETEC, nullable.
--
-- 2) control_etec.horas_referencia_cargo
--    Cantidad de horas de referencia de los cargos por puntos del período.
--
-- 3) control_etec.basico_maestro_grado
--    Valor del básico del maestro de grado del período. Es la base contra la que se expresa
--    el multiplicador `veces_basico_maestro_grado` del cargo asignado.
--
-- 4) etec_asignacion_cargo.horas_cargo
--    Cantidad de horas del cargo asignado al legajo.
--
-- 5) etec_asignacion_cargo.veces_basico_maestro_grado
--    Multiplicador del básico del maestro de grado para el cargo asignado. Puede ser 0 y
--    admite dos decimales (valores previstos: 2.00, 1.00, 0.50).
--
-- La aplicación NO crea ni migra el esquema (ddl-auto: none).
-- Idempotente para MySQL 5.7 (no soporta ADD COLUMN IF NOT EXISTS).

-- 1) persona.alta_etec
SET @existe := (
    SELECT COUNT(*)
    FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'persona'
      AND COLUMN_NAME = 'alta_etec'
);
SET @sql := IF(@existe = 0,
    'ALTER TABLE persona ADD COLUMN alta_etec DATETIME NULL AFTER alta_administrativa',
    'SELECT "persona.alta_etec: ya existe, nada que hacer" AS resultado');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 1.b) Si la columna se creó previamente como DATETIME(6), se alinea con alta_docente y
--      alta_administrativa, que son DATETIME sin precisión.
SET @precision := (
    SELECT IFNULL(MAX(DATETIME_PRECISION), 0)
    FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'persona'
      AND COLUMN_NAME = 'alta_etec'
);
SET @sql := IF(@precision > 0,
    'ALTER TABLE persona MODIFY COLUMN alta_etec DATETIME NULL',
    'SELECT "persona.alta_etec: ya es DATETIME, nada que hacer" AS resultado');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 1.c) La vista de búsqueda enumera columnas: se recrea con alta_etec para que el
--      buscador de personas también la devuelva.
CREATE OR REPLACE VIEW vw_persona_search AS
SELECT CONCAT(p.legajo_id, ' ', p.apellido, ' ', p.nombre) AS search,
       p.legajo_id AS legajo_id,
       p.documento AS documento,
       p.apellido AS apellido,
       p.nombre AS nombre,
       p.nacimiento AS nacimiento,
       p.alta_docente AS alta_docente,
       p.ajuste_docente AS ajuste_docente,
       p.alta_administrativa AS alta_administrativa,
       p.alta_etec AS alta_etec,
       p.ajuste_administrativo AS ajuste_administrativo,
       p.estado_civil AS estado_civil,
       p.situacion_id AS situacion_id,
       p.reemplazo_desarraigo AS reemplazo_desarraigo,
       p.mitad_desarraigo AS mitad_desarraigo,
       p.cuil AS cuil,
       p.posgrado AS posgrado,
       p.estado AS estado,
       p.liquida AS liquida,
       p.estado_afip AS estado_afip,
       p.dependencia_id AS dependencia_id,
       p.salida AS salida,
       p.obra_social AS obra_social,
       p.actividad_afip AS actividad_afip,
       p.localidad_afip AS localidad_afip,
       p.situacion_afip AS situacion_afip,
       p.modelo_contratacion_afip AS modelo_contratacion_afip,
       p.created AS created,
       p.updated AS updated
FROM persona p
WHERE p.apellido <> '';

-- 2) control_etec.horas_referencia_cargo
SET @existe := (
    SELECT COUNT(*)
    FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'control_etec'
      AND COLUMN_NAME = 'horas_referencia_cargo'
);
SET @sql := IF(@existe = 0,
    'ALTER TABLE control_etec ADD COLUMN horas_referencia_cargo DECIMAL(6,2) NOT NULL DEFAULT 0.00 AFTER monto_fijo_cargo',
    'SELECT "control_etec.horas_referencia_cargo: ya existe, nada que hacer" AS resultado');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 3) control_etec.basico_maestro_grado
SET @existe := (
    SELECT COUNT(*)
    FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'control_etec'
      AND COLUMN_NAME = 'basico_maestro_grado'
);
SET @sql := IF(@existe = 0,
    'ALTER TABLE control_etec ADD COLUMN basico_maestro_grado DECIMAL(16,2) NOT NULL DEFAULT 0.00 AFTER horas_referencia_cargo',
    'SELECT "control_etec.basico_maestro_grado: ya existe, nada que hacer" AS resultado');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 4) etec_asignacion_cargo.horas_cargo
SET @existe := (
    SELECT COUNT(*)
    FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'etec_asignacion_cargo'
      AND COLUMN_NAME = 'horas_cargo'
);
SET @sql := IF(@existe = 0,
    'ALTER TABLE etec_asignacion_cargo ADD COLUMN horas_cargo DECIMAL(6,2) NOT NULL DEFAULT 0.00 AFTER categoria_id',
    'SELECT "etec_asignacion_cargo.horas_cargo: ya existe, nada que hacer" AS resultado');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 5) etec_asignacion_cargo.veces_basico_maestro_grado
SET @existe := (
    SELECT COUNT(*)
    FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'etec_asignacion_cargo'
      AND COLUMN_NAME = 'veces_basico_maestro_grado'
);
SET @sql := IF(@existe = 0,
    'ALTER TABLE etec_asignacion_cargo ADD COLUMN veces_basico_maestro_grado DECIMAL(6,2) NOT NULL DEFAULT 0.00 AFTER horas_cargo',
    'SELECT "etec_asignacion_cargo.veces_basico_maestro_grado: ya existe, nada que hacer" AS resultado');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
