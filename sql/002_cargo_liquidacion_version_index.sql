-- Índice para el contador de versiones de cargo_liquidacion y la reconstrucción por
-- legajo/período. Sin este índice, cada generación hace un scan de la tabla (1M+ filas)
-- para calcular MAX(version).
--
-- MySQL 5.7 no soporta CREATE INDEX IF NOT EXISTS: se hace idempotente vía
-- information_schema. Aplicar a mano antes de desplegar la versión que escribe versiones.

SET @schema := DATABASE();
SET @exists := (
    SELECT COUNT(*)
    FROM information_schema.statistics
    WHERE table_schema = @schema
      AND table_name = 'cargo_liquidacion_version'
      AND index_name = 'idx_cargo_liq_version_legajo_periodo'
);
SET @ddl := IF(@exists = 0,
    'CREATE INDEX idx_cargo_liq_version_legajo_periodo ON cargo_liquidacion_version (legajo_id, anho, mes, version)',
    'SELECT ''idx_cargo_liq_version_legajo_periodo ya existe''');
PREPARE stmt FROM @ddl;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
