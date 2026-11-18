-- Retiro de control.valor_punto_etec.
-- El valor del punto pasó a control_etec junto con el monto fijo del cargo y los
-- porcentajes del circuito ETEC (presentismo, adicional carrera, garantía y carga
-- horaria). La generación y la UI ya leen control_etec.
-- Idempotente: sólo actúa si la columna existe. Ejecutar después del deploy del
-- código que ya no mapea el campo (ddl-auto: none).

SET @existe := (
    SELECT COUNT(*)
    FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'control'
      AND COLUMN_NAME = 'valor_punto_etec'
);

SET @sql := IF(@existe > 0,
    'ALTER TABLE control DROP COLUMN valor_punto_etec',
    'SELECT "control.valor_punto_etec: no existe, nada que hacer" AS resultado');

PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
