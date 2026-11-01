-- Tabla de outbox para los tests (H2). En MySQL el DDL real está en sql/001_audit_outbox.sql.
CREATE TABLE IF NOT EXISTS audit_outbox (
    outbox_id       BIGINT       NOT NULL AUTO_INCREMENT,
    event_id        VARCHAR(36)  NOT NULL,
    payload         CLOB         NOT NULL,
    status          VARCHAR(12)  NOT NULL DEFAULT 'PENDING',
    attempts        INT          NOT NULL DEFAULT 0,
    next_attempt_at TIMESTAMP    NOT NULL,
    claim_token     VARCHAR(36)  NULL,
    created         TIMESTAMP    NOT NULL,
    sent_at         TIMESTAMP    NULL,
    last_error      VARCHAR(500) NULL,
    PRIMARY KEY (outbox_id),
    CONSTRAINT uk_audit_outbox_event UNIQUE (event_id)
);
