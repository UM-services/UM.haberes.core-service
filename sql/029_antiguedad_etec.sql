-- Antigüedad ETEC por período.
--
-- Hasta ahora `antiguedad` guardaba meses docentes y administrativos. Se agrega
-- `meses_etec`, calculado desde `persona.alta_etec` igual que los otros dos orígenes:
--
--   meses_etec = meses desde alta_etec hasta el período de la fila (0 si es anterior)
--
-- La columna se expone en `vw_antiguedad_periodo` para que el frontend muestre el tercer
-- bloque del cuadro de antigüedades.
--
-- Idempotente para MySQL 5.7. Incluye backfill de las filas existentes que todavía estén en 0.

-- 1) Columna en la tabla.
SET @existe := (
    SELECT COUNT(*)
    FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'antiguedad'
      AND COLUMN_NAME = 'meses_etec'
);
SET @sql := IF(@existe = 0,
    'ALTER TABLE antiguedad ADD COLUMN meses_etec SMALLINT(2) NOT NULL DEFAULT 0 AFTER meses_administrativos',
    'SELECT "antiguedad.meses_etec: ya existe, nada que hacer" AS resultado');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 2) Backfill de las filas históricas que todavía no tienen ETEC calculado.
UPDATE antiguedad a
JOIN persona p ON p.legajo_id = a.legajo_id
SET a.meses_etec = GREATEST(0, (a.anho - YEAR(p.alta_etec)) * 12 + (a.mes - MONTH(p.alta_etec)))
WHERE p.alta_etec IS NOT NULL
  AND a.meses_etec = 0;

-- 3) La vista enumera columnas: se recrea con meses_etec.
CREATE OR REPLACE VIEW vw_antiguedad_periodo AS
SELECT a.antiguedad_id AS antiguedad_id,
       a.legajo_id AS legajo_id,
       a.anho AS anho,
       a.mes AS mes,
       a.meses_docentes AS meses_docentes,
       a.meses_administrativos AS meses_administrativos,
       a.meses_etec AS meses_etec,
       (a.anho * 100 + a.mes) AS periodo,
       a.created AS created,
       a.updated AS updated
FROM antiguedad a;
