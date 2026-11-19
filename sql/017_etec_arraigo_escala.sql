-- Escala de arraigo ETEC por antigüedad docente.
--
-- El arraigo ETEC es un porcentaje del valor del cargo según los años de antigüedad docente
-- (`antiguedad.meses_docentes / 12`, con división entera). La escala es global, no por período:
--   * 2 años -> 20 %
--   * 3 años -> 25 %
--   * 4 años -> 30 %
--   * 5 años -> 35 %
--   * 6 años -> 40 %
--   * 7 años -> 45 %
--   * 8 años -> 50 %
--   * 9 años -> 55 %
--   * 10 años o más -> 60 % (se usa el último tramo)
--   * menos de 2 años -> no se liquida arraigo
--
-- Se edita desde Administrador > Control del período > ETEC (tabla global).
-- Idempotente: crea la tabla si no existe y sólo inserta los tramos que faltan.

CREATE TABLE IF NOT EXISTS etec_arraigo_escala (
  etec_arraigo_escala_id INT(4) NOT NULL AUTO_INCREMENT,
  anios_minimos INT(3) NOT NULL,
  porcentaje DECIMAL(6,2) NOT NULL DEFAULT 0.00,
  created DATETIME(6) NOT NULL,
  updated DATETIME(6) DEFAULT NULL,
  PRIMARY KEY (etec_arraigo_escala_id),
  UNIQUE KEY uq_etec_arraigo_anios (anios_minimos)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO etec_arraigo_escala (anios_minimos, porcentaje, created)
SELECT tramo.anios, tramo.porcentaje, NOW(6)
FROM (
  SELECT 2 AS anios, 20.00 AS porcentaje
  UNION ALL SELECT 3, 25.00
  UNION ALL SELECT 4, 30.00
  UNION ALL SELECT 5, 35.00
  UNION ALL SELECT 6, 40.00
  UNION ALL SELECT 7, 45.00
  UNION ALL SELECT 8, 50.00
  UNION ALL SELECT 9, 55.00
  UNION ALL SELECT 10, 60.00
) tramo
WHERE NOT EXISTS (
  SELECT 1 FROM etec_arraigo_escala escala WHERE escala.anios_minimos = tramo.anios
);
