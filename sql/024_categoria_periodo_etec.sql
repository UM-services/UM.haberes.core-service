-- Versionado ETEC de categoria_periodo: puntos del cargo y flag de horas de clase.
--
-- A partir de este cambio, la generación ETEC resuelve puntos y clasificación por período
-- (categoria_periodo) con fallback a categoria cuando el período no tiene fila.
--
-- Idempotente. El backfill es de una sola vez: replica el comportamiento previo, que
-- aplicaba los valores actuales de categoria a todos los períodos.

SET @col_exists := (
    SELECT COUNT(*) FROM information_schema.columns
    WHERE table_schema = DATABASE()
      AND table_name = 'categoria_periodo'
      AND column_name = 'puntos'
);
SET @ddl := IF(@col_exists = 0,
    'ALTER TABLE categoria_periodo ADD COLUMN puntos DECIMAL(10,2) NOT NULL DEFAULT 0.00 AFTER estado_docente',
    'SELECT ''categoria_periodo.puntos ya existe''');
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @col_exists := (
    SELECT COUNT(*) FROM information_schema.columns
    WHERE table_schema = DATABASE()
      AND table_name = 'categoria_periodo'
      AND column_name = 'etec_horas'
);
SET @ddl := IF(@col_exists = 0,
    'ALTER TABLE categoria_periodo ADD COLUMN etec_horas TINYINT(1) NOT NULL DEFAULT 0 AFTER puntos',
    'SELECT ''categoria_periodo.etec_horas ya existe''');
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- Backfill único (sólo filas todavía en 0).
UPDATE categoria_periodo cp
JOIN categoria c ON c.categoria_id = cp.categoria_id
SET cp.puntos = c.puntos
WHERE cp.puntos = 0.00
  AND c.puntos > 0.00;

UPDATE categoria_periodo cp
JOIN categoria c ON c.categoria_id = cp.categoria_id
SET cp.etec_horas = c.etec_horas
WHERE cp.etec_horas = 0
  AND c.etec_horas = 1;
