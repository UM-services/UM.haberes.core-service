-- Índice para la detección de novedades duplicadas por período.
--
-- La consulta de `deleteNovedadDuplicada` filtra por anho/mes y agrupa por legajo/código. Sin
-- este índice, MySQL resuelve el período sobre 377k filas y termina en `Using temporary;
-- Using filesort` (~0,42 s). Con el índice, el rango del período y el `group by` se resuelven
-- por el mismo árbol (~0,4 ms).
--
-- Idempotente para MySQL 5.7 (no soporta CREATE INDEX IF NOT EXISTS).

SET @schema := DATABASE();
SET @exists := (
    SELECT COUNT(*)
    FROM information_schema.statistics
    WHERE table_schema = @schema
      AND table_name = 'novedad'
      AND index_name = 'idx_novedad_periodo_dup'
);
SET @ddl := IF(@exists = 0,
    'CREATE INDEX idx_novedad_periodo_dup ON novedad (anho, mes, dependencia_id, legajo_id, codigo_id)',
    'SELECT ''idx_novedad_periodo_dup ya existe''');
PREPARE stmt FROM @ddl;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
