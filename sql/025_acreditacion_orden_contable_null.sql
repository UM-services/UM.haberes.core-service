-- `acreditacion.orden_contable` puede quedar en NULL mientras no se generó el asiento.
--
-- La pantalla Límite de Novedades da de alta el período sólo con el límite, sin orden
-- contable. Como la columna era NOT NULL, el INSERT explícito con NULL fallaba con
-- "Column 'orden_contable' cannot be null" (el DEFAULT 0 de MySQL no aplica a un NULL
-- explícito).
--
-- A partir de este cambio NULL significa "todavía sin asiento"; 0 queda como valor
-- heredado y se mantiene el DEFAULT 0 para los inserts que omiten la columna.
--
-- Idempotente: sólo modifica la columna si hoy es NOT NULL; puede ejecutarse más de una vez.

SET @nullable := (
    SELECT IS_NULLABLE
    FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'acreditacion'
      AND COLUMN_NAME = 'orden_contable'
);
SET @sql := IF(@nullable = 'NO',
    'ALTER TABLE acreditacion MODIFY orden_contable SMALLINT(2) NULL DEFAULT 0',
    'SELECT "acreditacion.orden_contable: ya admite NULL, nada que hacer" AS resultado');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
