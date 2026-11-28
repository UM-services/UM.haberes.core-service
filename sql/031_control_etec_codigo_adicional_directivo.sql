-- Código de liquidación del adicional directivo ETEC.
--
-- El concepto no lleva un `codigo_id` fijo en el código ni en los scripts: el período guarda
-- en `control_etec.codigo_adicional_directivo` el código del catálogo que corresponde liquidar.
-- El código se crea o se elige en la pantalla de Códigos; acá sólo se enlaza por nombre el que
-- ya exista, sin fijar el número.
--
-- Idempotente para MySQL 5.7.

SET @existe := (
    SELECT COUNT(*)
    FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'control_etec'
      AND COLUMN_NAME = 'codigo_adicional_directivo'
);
SET @sql := IF(@existe = 0,
    'ALTER TABLE control_etec ADD COLUMN codigo_adicional_directivo INT NULL AFTER basico_maestro_grado',
    'SELECT "control_etec.codigo_adicional_directivo: ya existe, nada que hacer" AS resultado');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Enlaza el código existente por nombre. Si no existe, el período queda sin adicional directivo
-- hasta que se cree el código y se configure en Control ETEC.
UPDATE control_etec
SET codigo_adicional_directivo = (
    SELECT c.codigo_id
    FROM codigo c
    WHERE c.nombre = 'ADICIONAL DIRECTIVO ETEC'
    ORDER BY c.codigo_id
    LIMIT 1
)
WHERE codigo_adicional_directivo IS NULL
  AND EXISTS (
      SELECT 1 FROM codigo c WHERE c.nombre = 'ADICIONAL DIRECTIVO ETEC'
  );
