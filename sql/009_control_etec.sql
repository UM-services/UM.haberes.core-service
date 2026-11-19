-- Control propio de la liquidación ETEC por puntos.
--
-- Hasta ahora el valor del punto vivía en `control.valor_punto_etec`; a partir de este script
-- los parámetros ETEC tienen su propia tabla para no mezclarlos con la liquidación general:
--   * valor_punto_etec                     -> puntos x valor para el cargo
--   * monto_fijo_cargo                     -> importe fijo que se suma al cargo
--   * porcentaje_presentismo               -> presentismo ETEC sobre el cargo
--   * porcentaje_adicional_carrera         -> adicional carrera docente sobre cargo + antigüedad
--   * garantia                             -> piso de cargo + antigüedad + presentismo
--   * horas_adicional_carga_horaria        -> horas del adicional de carga horaria
--   * valor_hora_adicional_carga_horaria   -> valor de cada hora adicional
--
-- Idempotente: crea la tabla si no existe y sólo inserta los períodos que todavía no tienen
-- fila. El backfill toma el valor del punto de `control` para no perder lo ya cargado.

CREATE TABLE IF NOT EXISTS control_etec (
  control_etec_id INT(4) NOT NULL AUTO_INCREMENT,
  anho SMALLINT(2) NOT NULL DEFAULT 0,
  mes SMALLINT(2) NOT NULL DEFAULT 0,
  valor_punto_etec DECIMAL(16,4) NOT NULL DEFAULT 0.0000,
  monto_fijo_cargo DECIMAL(16,2) NOT NULL DEFAULT 0.00,
  porcentaje_presentismo DECIMAL(6,2) NOT NULL DEFAULT 20.00,
  porcentaje_adicional_carrera DECIMAL(6,2) NOT NULL DEFAULT 10.00,
  garantia DECIMAL(16,2) NOT NULL DEFAULT 0.00,
  horas_adicional_carga_horaria DECIMAL(6,2) NOT NULL DEFAULT 0.00,
  valor_hora_adicional_carga_horaria DECIMAL(16,2) NOT NULL DEFAULT 0.00,
  created DATETIME(6) NOT NULL,
  updated DATETIME(6) DEFAULT NULL,
  PRIMARY KEY (control_etec_id),
  UNIQUE KEY uq_control_etec_periodo (anho, mes)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Parámetros iniciales del período vigente (2026/11).
INSERT INTO control_etec (anho, mes, valor_punto_etec, monto_fijo_cargo, porcentaje_presentismo,
                          porcentaje_adicional_carrera, garantia, horas_adicional_carga_horaria,
                          valor_hora_adicional_carga_horaria, created)
SELECT 2026, 11, 311.3633, 247543.34, 20.00, 10.00, 844872.78, 15.00, 8012.19, NOW(6)
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM control_etec WHERE anho = 2026 AND mes = 11);

-- Backfill del resto de los períodos que ya tenían valor del punto cargado en `control`.
INSERT INTO control_etec (anho, mes, valor_punto_etec, monto_fijo_cargo, porcentaje_presentismo,
                          porcentaje_adicional_carrera, garantia, horas_adicional_carga_horaria,
                          valor_hora_adicional_carga_horaria, created)
SELECT c.anho, c.mes, c.valor_punto_etec, 0.00, 20.00, 10.00, 0.00, 0.00, 0.00, NOW(6)
FROM control c
WHERE c.valor_punto_etec > 0
  AND NOT EXISTS (SELECT 1 FROM control_etec ce WHERE ce.anho = c.anho AND ce.mes = c.mes);
