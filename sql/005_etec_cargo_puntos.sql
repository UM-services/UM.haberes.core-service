-- Cargo por puntos ETEC.
--
-- Esquema y datos gestionados exclusivamente por haberes-core.
-- haberes-etec NO abre conexión a la base: opera por API contra core.
-- La aplicación no crea ni migra el esquema (ddl-auto: none).
--
-- Idempotente para MySQL 5.7 (no soporta ADD COLUMN IF NOT EXISTS).

-- 1) Puntaje del cargo en la categoría (0 = categoría común).
SET @schema := DATABASE();
SET @exists := (
    SELECT COUNT(*)
    FROM information_schema.columns
    WHERE table_schema = @schema
      AND table_name = 'categoria'
      AND column_name = 'puntos'
);
SET @ddl := IF(@exists = 0,
    'ALTER TABLE categoria ADD COLUMN puntos DECIMAL(10,2) NOT NULL DEFAULT 0.00',
    'SELECT ''categoria.puntos ya existe''');
PREPARE stmt FROM @ddl;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 2) Asignación del cargo con puntos a un legajo (alta / baja).
CREATE TABLE IF NOT EXISTS etec_asignacion_cargo (
    asignacion_cargo_id BIGINT       NOT NULL AUTO_INCREMENT,
    legajo_id           INT          NOT NULL,
    categoria_id        SMALLINT     NOT NULL,
    dependencia_id      SMALLINT     NULL,
    fecha_alta          DATETIME(6)  NOT NULL,
    fecha_baja          DATETIME(6)  NULL,
    observaciones       VARCHAR(255) NULL,
    created             DATETIME(6)  NOT NULL,
    updated             DATETIME(6)  NULL,
    PRIMARY KEY (asignacion_cargo_id),
    UNIQUE KEY uk_etec_asig_legajo_categoria_alta (legajo_id, categoria_id, fecha_alta),
    KEY idx_etec_asig_legajo (legajo_id, fecha_alta, fecha_baja),
    KEY idx_etec_asig_categoria (categoria_id),
    CONSTRAINT fk_etec_asig_persona
        FOREIGN KEY (legajo_id) REFERENCES persona (legajo_id),
    CONSTRAINT fk_etec_asig_dependencia
        FOREIGN KEY (dependencia_id) REFERENCES dependencia (dependencia_id),
    CONSTRAINT fk_etec_asig_categoria
        FOREIGN KEY (categoria_id) REFERENCES categoria (categoria_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
