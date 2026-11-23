-- Categoría por tramo de la escala de horas ETEC.
--
-- Hasta ahora el cargo por horas de clase se generaba siempre con la categoría 2002
-- (hardcodeada en haberes-core). A partir de este cambio cada tramo de `etec_hora_escala`
-- indica con qué categoría se liquida, y la categoría se marca con `etec_horas = 1` para
-- que el catálogo de universos y la liquidación la reconozcan por dato y no por id.
--
-- El backfill asigna 2002 a los tramos existentes para conservar el comportamiento actual.
-- La categoría 2002 queda marcada como `etec_horas = 1`.
--
-- Idempotente: agrega columnas, índice y FK sólo si faltan; puede ejecutarse más de una vez.

-- 1. Flag de origen en la categoría.
SET @existe := (
    SELECT COUNT(*)
    FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'categoria'
      AND COLUMN_NAME = 'etec_horas'
);
SET @sql := IF(@existe = 0,
    'ALTER TABLE categoria ADD COLUMN etec_horas TINYINT(1) NOT NULL DEFAULT 0 AFTER puntos',
    'SELECT "categoria.etec_horas: ya existe, nada que hacer" AS resultado');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 2. Categoría que liquida cada tramo de horas.
SET @existe := (
    SELECT COUNT(*)
    FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'etec_hora_escala'
      AND COLUMN_NAME = 'categoria_id'
);
SET @sql := IF(@existe = 0,
    'ALTER TABLE etec_hora_escala ADD COLUMN categoria_id SMALLINT NULL AFTER control_etec_id',
    'SELECT "etec_hora_escala.categoria_id: ya existe, nada que hacer" AS resultado');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

UPDATE etec_hora_escala
SET categoria_id = 2002
WHERE categoria_id IS NULL;

-- 3. Todo tramo debe liquidar con una categoría.
SET @nullable := (
    SELECT IS_NULLABLE
    FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'etec_hora_escala'
      AND COLUMN_NAME = 'categoria_id'
);
SET @sql := IF(@nullable = 'YES',
    'ALTER TABLE etec_hora_escala MODIFY categoria_id SMALLINT NOT NULL',
    'SELECT "etec_hora_escala.categoria_id: ya es NOT NULL" AS resultado');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 4. Índice por categoría (para los filtros del catálogo y la generación).
SET @existe := (
    SELECT COUNT(*)
    FROM information_schema.STATISTICS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'etec_hora_escala'
      AND INDEX_NAME = 'idx_etec_hora_escala_categoria'
);
SET @sql := IF(@existe = 0,
    'ALTER TABLE etec_hora_escala ADD KEY idx_etec_hora_escala_categoria (categoria_id)',
    'SELECT "idx_etec_hora_escala_categoria: ya existe, nada que hacer" AS resultado');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 5. FK a la categoría.
SET @existe := (
    SELECT COUNT(*)
    FROM information_schema.TABLE_CONSTRAINTS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'etec_hora_escala'
      AND CONSTRAINT_NAME = 'fk_etec_hora_escala_categoria'
);
SET @sql := IF(@existe = 0,
    'ALTER TABLE etec_hora_escala ADD CONSTRAINT fk_etec_hora_escala_categoria FOREIGN KEY (categoria_id) REFERENCES categoria (categoria_id)',
    'SELECT "fk_etec_hora_escala_categoria: ya existe, nada que hacer" AS resultado');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 6. La categoría histórica de horas de clase ETEC.
UPDATE categoria
SET etec_horas = 1
WHERE categoria_id = 2002;
