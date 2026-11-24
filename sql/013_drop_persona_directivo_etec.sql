-- Retiro de persona.directivo_etec.
-- El flag sólo excluía a los directivos del desglose ETEC del bono, que ya no existe.
-- Se retiró también del contrato JSON de persona: `PersonaResponse` ya no expone el campo.
-- Idempotente: sólo actúa si la columna existe.

SET @existe := (
    SELECT COUNT(*)
    FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'persona'
      AND COLUMN_NAME = 'directivo_etec'
);

SET @sql := IF(@existe > 0,
    'ALTER TABLE persona DROP COLUMN directivo_etec',
    'SELECT "persona.directivo_etec: no existe, nada que hacer" AS resultado');

PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
