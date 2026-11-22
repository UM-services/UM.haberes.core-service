-- Pérdida de presentismo de los cargos ETEC por horas de un legajo.
--
-- Los cargos por horas (categoría 2002) se generan desde los cursos de la facultad 15 y
-- no tienen `etec_asignacion_cargo`. Por eso la pérdida se registra por legajo y período:
-- una fila descuenta el presentismo de todos los cargos por horas del legajo en el período.
--
-- Sin ON DELETE CASCADE: la baja de la persona no debería ocurrir con cargos vigentes; si
-- ocurre, la FK obliga a resolverlo explícitamente. Esquema gestionado por haberes-core.

CREATE TABLE IF NOT EXISTS etec_presentismo_horas (
    presentismo_horas_id BIGINT       NOT NULL AUTO_INCREMENT,
    legajo_id            INT          NOT NULL,
    anho                 SMALLINT     NOT NULL,
    mes                  SMALLINT     NOT NULL,
    motivo               VARCHAR(255) NOT NULL,
    created              DATETIME(6)  NOT NULL,
    updated              DATETIME(6)  NULL,
    PRIMARY KEY (presentismo_horas_id),
    UNIQUE KEY uk_etec_presentismo_horas_legajo_periodo (legajo_id, anho, mes),
    CONSTRAINT fk_etec_presentismo_horas_persona
        FOREIGN KEY (legajo_id) REFERENCES persona (legajo_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
