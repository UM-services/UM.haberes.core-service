-- Retiro de control.hora_referencia_etec.
-- El desglose ETEC del bono se eliminó: el concepto 29 se imprime como una línea
-- normal con su importe real y ya no se calcula el básico horario ficticio.
-- Idempotente: sólo actúa si la columna existe. Ejecutar después del deploy del
-- código que ya no mapea el campo (ddl-auto: none).

SET @existe := (
    SELECT COUNT(*)
    FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'control'
      AND COLUMN_NAME = 'hora_referencia_etec'
);

SET @sql := IF(@existe > 0,
    'ALTER TABLE control DROP COLUMN hora_referencia_etec',
    'SELECT "control.hora_referencia_etec: no existe, nada que hacer" AS resultado');

PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
