-- Adicional Directivo ETEC.
--
-- Concepto propio de los cargos ETEC por puntos que tienen informado
-- `etec_asignacion_cargo.veces_basico_maestro_grado`:
--
--   importe = control_etec.basico_maestro_grado × veces_basico_maestro_grado
--
-- Es remunerativo, docente e incluido en ETEC, igual que el resto de los conceptos del circuito.
-- Idempotente: sólo inserta si el código no existe.

INSERT INTO codigo (codigo_id, nombre, docente, no_docente, transferible, incluido_etec,
                    afip_concepto_sueldo_id_primer_semestre, afip_concepto_sueldo_id_segundo_semestre,
                    created)
SELECT 990, 'ADICIONAL DIRECTIVO ETEC', 1, 0, 0, 1, 110000, 110000, NOW() FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM codigo WHERE codigo_id = 990);

INSERT INTO codigo_grupo (codigo_id, remunerativo, no_remunerativo, deduccion, total, created)
SELECT 990, 1, 0, 0, 0, NOW() FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM codigo_grupo WHERE codigo_id = 990);
