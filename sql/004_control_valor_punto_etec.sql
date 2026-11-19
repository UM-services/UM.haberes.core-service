-- Valor del punto de referencia ETEC en el control del período.
--
-- Puente operativo mientras haberes-etec no implemente etec_valor_punto
-- (ver PLANIFICACION_CARGO_PUNTOS_ETEC.md). La liquidación de bono/report todavía
-- no lo consume: sólo se persiste y se copia al copiar el período.
--
-- La aplicación NO crea ni migra el esquema (ddl-auto: none). Idempotente para
-- MySQL 5.7, que no soporta ADD COLUMN IF NOT EXISTS.

SET @schema := DATABASE();
SET @exists := (
    SELECT COUNT(*)
    FROM information_schema.columns
    WHERE table_schema = @schema
      AND table_name = 'control'
      AND column_name = 'valor_punto_etec'
);
SET @ddl := IF(@exists = 0,
    'ALTER TABLE control ADD COLUMN valor_punto_etec DECIMAL(14,2) NOT NULL DEFAULT 0.00',
    'SELECT ''valor_punto_etec ya existe''');
PREPARE stmt FROM @ddl;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
