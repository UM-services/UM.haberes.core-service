-- Arraigo ETEC: concepto propio de la liquidación ETEC por puntos.
--
-- El porcentaje se resuelve con la escala global por antigüedad docente
-- (`etec_arraigo_escala`, sql/017). Es remunerativo, con `docente = 1`
-- para que la contabilidad lo impute a las cuentas docentes, `incluido_etec = 1` para que cuente
-- como concepto ETEC y el concepto AFIP 110000 (sueldo) en ambos semestres. Idempotente: sólo
-- inserta si el código no existe.

INSERT INTO codigo (codigo_id, nombre, docente, no_docente, transferible, incluido_etec,
                    afip_concepto_sueldo_id_primer_semestre, afip_concepto_sueldo_id_segundo_semestre,
                    created)
SELECT 989, 'ARRAIGO ETEC', 1, 0, 0, 1, 110000, 110000, NOW() FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM codigo WHERE codigo_id = 989);

INSERT INTO codigo_grupo (codigo_id, remunerativo, no_remunerativo, deduccion, total, created)
SELECT 989, 1, 0, 0, 0, NOW() FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM codigo_grupo WHERE codigo_id = 989);
