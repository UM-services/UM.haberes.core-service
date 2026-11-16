-- Procesos masivos de liquidación general (padrón de todo el universo liquidable).
--
-- La operación es asíncrona: el proceso se persiste para que el cliente pueda consultar su
-- avance, el resultado por legajo y sobreviva a un reinicio del servicio. El detalle guarda
-- sólo los resultados relevantes (omitidos, fallidos, liquidados, eliminados y generados).
--
-- Idempotente: crea las tablas si no existen. La aplicación NO crea ni migra el esquema
-- (ddl-auto: none).

CREATE TABLE IF NOT EXISTS liquidacion_general_proceso (
  proceso_id VARCHAR(36) NOT NULL,
  tipo VARCHAR(16) NOT NULL,
  anho INT(4) NOT NULL,
  mes INT(2) NOT NULL,
  estado VARCHAR(16) NOT NULL,
  total INT(6) NOT NULL DEFAULT 0,
  procesados INT(6) NOT NULL DEFAULT 0,
  exitosos INT(6) NOT NULL DEFAULT 0,
  omitidos INT(6) NOT NULL DEFAULT 0,
  fallidos INT(6) NOT NULL DEFAULT 0,
  forzar TINYINT(1) NOT NULL DEFAULT 0,
  iniciado_por BIGINT NULL,
  iniciado_en DATETIME(6) NOT NULL,
  finalizado_en DATETIME(6) NULL,
  mensaje VARCHAR(500) NULL,
  PRIMARY KEY (proceso_id),
  KEY idx_liq_general_periodo (anho, mes, tipo, estado)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS liquidacion_general_detalle (
  detalle_id BIGINT NOT NULL AUTO_INCREMENT,
  proceso_id VARCHAR(36) NOT NULL,
  legajo_id BIGINT NOT NULL,
  apellido VARCHAR(120) NULL,
  nombre VARCHAR(120) NULL,
  resultado VARCHAR(16) NOT NULL,
  motivo VARCHAR(48) NULL,
  detalle VARCHAR(500) NULL,
  total_neto DECIMAL(15,2) NULL,
  registrado_en DATETIME(6) NOT NULL,
  PRIMARY KEY (detalle_id),
  KEY idx_liq_general_detalle_proceso (proceso_id, resultado),
  KEY idx_liq_general_detalle_legajo (legajo_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
