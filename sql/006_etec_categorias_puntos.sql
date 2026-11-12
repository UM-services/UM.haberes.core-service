-- Migración de categorías ETEC al modelo de puntos.
--
-- Fuente: /lungoh8Oopeiwahl/dropbox/UM/etec/Escala Hs Catedra Secund USADA.xls
--         hoja "Escala DIRECTIVOS" (bloques con índice 3.5558 y 6.52; los puntos
--         del cargo son los mismos en ambos).
--
-- Puntos del cargo:
--   Director        1738
--   Vice            1469
--   Secretario      1163
--   Jefe Preceptor  1000
--   Preceptor        975
--   Jefe Laboratorio 1000
--
-- Mapeo a las categorías ETEC existentes en haberes.categoria:
--   992 DIRECTOR ETEC       -> Director        1738
--   993 VICEDIRECTORA ETEC  -> Vice            1469
--   994 DIR DE EST ETEC     -> 1000 (rol no listado en la hoja; se usa el valor
--                                   de Jefe Preceptor/Laboratorio; CONFIRMAR)
--   995 SECRETARIO ETEC     -> Secretario      1163
--   991 DOCENTE ETEC        -> NO se migra: se liquida por horas (cargo_clase),
--                              no por puntos
--    94 VICE DIRECTOR ETEC  -> NO se migra: duplicado legacy no docente de 993
--
-- El importe de la categoría se deja en 0: el básico sale de puntos ×
-- control.valor_punto_etec al generar cargo_liquidacion.
--
-- Idempotente. Ejecutar con haberes-core detenido o en una ventana sin altas.

START TRANSACTION;

UPDATE categoria
SET puntos = 1738.00, basico = 0.00, docente = 1, no_docente = 0,
    liquida_por_hora = 0, estado_docente = 0.00
WHERE categoria_id = 992;

UPDATE categoria
SET puntos = 1469.00, basico = 0.00, docente = 1, no_docente = 0,
    liquida_por_hora = 0, estado_docente = 0.00
WHERE categoria_id = 993;

UPDATE categoria
SET puntos = 1000.00, basico = 0.00, docente = 1, no_docente = 0,
    liquida_por_hora = 0, estado_docente = 0.00
WHERE categoria_id = 994;

UPDATE categoria
SET puntos = 1163.00, basico = 0.00, docente = 1, no_docente = 0,
    liquida_por_hora = 0, estado_docente = 0.00
WHERE categoria_id = 995;

-- Limpieza de la categoría de prueba creada en la validación E2E (1307).
-- No tiene cargos vivos; sí versiones, una asignación y filas de categoría-período.
DELETE FROM cargo_liquidacion_version WHERE categoria_id = 1307;
DELETE FROM cargo_liquidacion WHERE categoria_id = 1307;
DELETE FROM etec_asignacion_cargo WHERE categoria_id = 1307;
DELETE FROM categoria_periodo WHERE categoria_id = 1307;
DELETE FROM categoria WHERE categoria_id = 1307;

COMMIT;
