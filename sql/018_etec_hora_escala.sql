-- Escala de horas de clase ETEC por período.
--
-- Los docentes de la Escuela Técnica (facultad 15) liquidan sus horas de clase con un importe
-- que depende del total de horas semanales designadas en `curso_cargo`:
--
--   importe = monto_fijo + horas × valor_hora
--
-- El tramo se elige por rango de horas (extremos inclusive) y la escala cuelga de
-- `control_etec`, porque los valores se negocian período a período. Las horas se suman por sede
-- (facultad 15 + geográfica) y generan un cargo con la categoría 2002 DOCENTE ETEC HORAS en
-- `cargo_liquidacion`.
--
-- La categoría 2002 no tiene puntos: su básico sale del importe del tramo. Se marca docente
-- para que el cargo acumule presentismo, adicional de carrera y arraigo ETEC.
--
-- Idempotente: crea la tabla si no existe y sólo inserta la categoría que falta.

CREATE TABLE IF NOT EXISTS etec_hora_escala (
  etec_hora_escala_id INT(4) NOT NULL AUTO_INCREMENT,
  control_etec_id INT(4) NOT NULL,
  horas_desde DECIMAL(6,2) NOT NULL DEFAULT 0.00,
  horas_hasta DECIMAL(6,2) NOT NULL DEFAULT 0.00,
  monto_fijo DECIMAL(16,2) NOT NULL DEFAULT 0.00,
  valor_hora DECIMAL(16,2) NOT NULL DEFAULT 0.00,
  created DATETIME(6) NOT NULL,
  updated DATETIME(6) DEFAULT NULL,
  PRIMARY KEY (etec_hora_escala_id),
  UNIQUE KEY uq_etec_hora_escala_desde (control_etec_id, horas_desde),
  KEY idx_etec_hora_escala_control (control_etec_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO categoria (categoria_id, nombre, basico, docente, no_docente, liquida_por_hora,
                       estado_docente, puntos, created)
SELECT 2002, 'DOCENTE ETEC HORAS', 0.00, 1, 0, 0, 0.00, 0.00, NOW(6)
FROM DUAL
WHERE NOT EXISTS (
  SELECT 1 FROM categoria WHERE categoria_id = 2002
);
