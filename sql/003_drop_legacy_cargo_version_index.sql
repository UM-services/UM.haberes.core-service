-- Elimina el índice legajo_id (legajo_id, anho, mes) de cargo_liquidacion_version: quedó
-- redundante porque idx_cargo_liq_version_legajo_periodo (legajo_id, anho, mes, version) lo
-- cubre como prefijo (y satisface la FK por legajo_id).
--
-- Idempotente para MySQL 5.7 (no soporta DROP INDEX IF EXISTS).

SET @schema := DATABASE();
SET @exists := (
    SELECT COUNT(*)
    FROM information_schema.statistics
    WHERE table_schema = @schema
      AND table_name = 'cargo_liquidacion_version'
      AND index_name = 'legajo_id'
);
SET @ddl := IF(@exists > 0,
    'ALTER TABLE cargo_liquidacion_version DROP INDEX legajo_id',
    'SELECT ''legajo_id ya no existe''');
PREPARE stmt FROM @ddl;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
