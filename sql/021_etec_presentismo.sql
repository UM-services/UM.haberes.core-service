-- Pérdida de presentismo de un cargo ETEC por período.
--
-- Una fila por asignación de cargo ETEC (etec_asignacion_cargo) y período. La existencia
-- de la fila significa que ese cargo pierde el presentismo del período; el motivo es
-- obligatorio. Al liquidar, el motor emite el 985 PRESENTISMO ETEC bruto y el 984
-- DESCUENTO PRESENTISMO ETEC por el mismo importe en negativo.
--
-- Sin ON DELETE CASCADE a propósito: la baja de la asignación borra primero estas filas
-- desde el caso de uso, para que cada borrado quede auditado. Esquema gestionado por
-- haberes-core (haberes-etec no abre conexión a la base).

CREATE TABLE IF NOT EXISTS etec_presentismo (
    presentismo_id      BIGINT       NOT NULL AUTO_INCREMENT,
    asignacion_cargo_id BIGINT       NOT NULL,
    anho                SMALLINT     NOT NULL,
    mes                 SMALLINT     NOT NULL,
    motivo              VARCHAR(255) NOT NULL,
    created             DATETIME(6)  NOT NULL,
    updated             DATETIME(6)  NULL,
    PRIMARY KEY (presentismo_id),
    UNIQUE KEY uk_etec_presentismo_asignacion_periodo (asignacion_cargo_id, anho, mes),
    KEY idx_etec_presentismo_periodo (anho, mes),
    CONSTRAINT fk_etec_presentismo_asignacion
        FOREIGN KEY (asignacion_cargo_id) REFERENCES etec_asignacion_cargo (asignacion_cargo_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
