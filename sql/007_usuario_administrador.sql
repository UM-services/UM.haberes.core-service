-- Flag administrador del usuario de Haberes.
--
-- Plan: PLANIFICACION_CAMPO_ADMINISTRADOR_USUARIO_HABERES.md (raiz del workspace).
-- Aplicado en haberes (MySQL 5.7.43) el 2026-11-12. Columnas nuevas siempre con
-- DEFAULT para no romper las escrituras del cliente legacy.
--
-- Semantica (decision D1): el modulo Administrador exige facultad_id IS NULL
-- AND administrador = 1. La asignacion inicial se hizo por lista explicita
-- (D2): legajos 725 y 1515.

ALTER TABLE usuario
  ADD COLUMN administrador TINYINT(1) NOT NULL DEFAULT 0 AFTER facultad_id;

-- Asignacion inicial (solo usuarios sin facultad, D5).
UPDATE usuario SET administrador = 1 WHERE legajo_id IN (725, 1515) AND facultad_id IS NULL;

-- Rollback:
-- ALTER TABLE usuario DROP COLUMN administrador;
