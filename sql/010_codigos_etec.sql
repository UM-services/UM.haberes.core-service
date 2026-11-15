-- Conceptos propios de la liquidación ETEC por puntos.
--
-- Los cuatro códigos son remunerativos, con `docente = 1` para que la contabilidad los impute a
-- las cuentas docentes, `incluido_etec = 1` para que cuenten como conceptos ETEC y el concepto
-- AFIP 110000 (sueldo) en ambos semestres. Idempotente: sólo inserta si el código no existe.

INSERT INTO codigo (codigo_id, nombre, docente, no_docente, transferible, incluido_etec,
                    afip_concepto_sueldo_id_primer_semestre, afip_concepto_sueldo_id_segundo_semestre,
                    created)
SELECT 985, 'PRESENTISMO ETEC', 1, 0, 0, 1, 110000, 110000, NOW() FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM codigo WHERE codigo_id = 985);

INSERT INTO codigo (codigo_id, nombre, docente, no_docente, transferible, incluido_etec,
                    afip_concepto_sueldo_id_primer_semestre, afip_concepto_sueldo_id_segundo_semestre,
                    created)
SELECT 986, 'ADICIONAL CARRERA DOCENTE ETEC', 1, 0, 0, 1, 110000, 110000, NOW() FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM codigo WHERE codigo_id = 986);

INSERT INTO codigo (codigo_id, nombre, docente, no_docente, transferible, incluido_etec,
                    afip_concepto_sueldo_id_primer_semestre, afip_concepto_sueldo_id_segundo_semestre,
                    created)
SELECT 987, 'GARANTIA ETEC', 1, 0, 0, 1, 110000, 110000, NOW() FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM codigo WHERE codigo_id = 987);

INSERT INTO codigo (codigo_id, nombre, docente, no_docente, transferible, incluido_etec,
                    afip_concepto_sueldo_id_primer_semestre, afip_concepto_sueldo_id_segundo_semestre,
                    created)
SELECT 988, 'ADICIONAL CARGA HORARIA ETEC', 1, 0, 0, 1, 110000, 110000, NOW() FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM codigo WHERE codigo_id = 988);

INSERT INTO codigo_grupo (codigo_id, remunerativo, no_remunerativo, deduccion, total, created)
SELECT 985, 1, 0, 0, 0, NOW() FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM codigo_grupo WHERE codigo_id = 985);

INSERT INTO codigo_grupo (codigo_id, remunerativo, no_remunerativo, deduccion, total, created)
SELECT 986, 1, 0, 0, 0, NOW() FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM codigo_grupo WHERE codigo_id = 986);

INSERT INTO codigo_grupo (codigo_id, remunerativo, no_remunerativo, deduccion, total, created)
SELECT 987, 1, 0, 0, 0, NOW() FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM codigo_grupo WHERE codigo_id = 987);

INSERT INTO codigo_grupo (codigo_id, remunerativo, no_remunerativo, deduccion, total, created)
SELECT 988, 1, 0, 0, 0, NOW() FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM codigo_grupo WHERE codigo_id = 988);
