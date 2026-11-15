-- Escala del valor del punto ETEC en el control del período.
--
-- El valor del punto se informa con cuatro decimales (por ejemplo 311.3633).
-- Con DECIMAL(14,2) el valor se redondeaba a 311.36 y el básico del cargo
-- (puntos × valor del punto) quedaba por debajo del real. Se amplía la escala
-- sin perder el valor existente: MySQL convierte 1000.00 en 1000.0000.
--
-- La aplicación NO crea ni migra el esquema (ddl-auto: none). Idempotente para
-- MySQL 5.7, que no soporta MODIFY COLUMN IF NOT EXISTS.

SET @schema := DATABASE();
SET @existe := (
    SELECT COUNT(*)
    FROM information_schema.columns
    WHERE table_schema = @schema
      AND table_name = 'control'
      AND column_name = 'valor_punto_etec'
);
SET @escala := (
    SELECT IFNULL(MAX(numeric_scale), 0)
    FROM information_schema.columns
    WHERE table_schema = @schema
      AND table_name = 'control'
      AND column_name = 'valor_punto_etec'
);
SET @ddl := IF(@existe = 0,
    'ALTER TABLE control ADD COLUMN valor_punto_etec DECIMAL(16,4) NOT NULL DEFAULT 0.0000',
    IF(@escala < 4,
        'ALTER TABLE control MODIFY COLUMN valor_punto_etec DECIMAL(16,4) NOT NULL DEFAULT 0.0000',
        'SELECT ''valor_punto_etec ya tiene escala de 4 decimales'''));
PREPARE stmt FROM @ddl;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
