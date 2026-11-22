-- Descuento de presentismo ETEC.
--
-- Concepto que acompaña al 985 PRESENTISMO ETEC cuando el cargo ETEC pierde el
-- presentismo del período: el motor emite el 985 bruto y el 984 por el mismo importe en
-- negativo. Es remunerativo (igual que el 17 de no docentes) para que reduzca el total
-- remunerativo antes de los aportes.
--
-- Se alinea con los demás conceptos ETEC (985-989): docente = 1, incluido_etec = 1 y
-- concepto AFIP 110000 (sueldo) en ambos semestres. Idempotente: crea el código si no
-- existe y normaliza sus flags si ya estaba cargado a mano.

INSERT INTO codigo (codigo_id, nombre, docente, no_docente, transferible, incluido_etec,
                    afip_concepto_sueldo_id_primer_semestre, afip_concepto_sueldo_id_segundo_semestre,
                    created)
SELECT 984, 'DESCUENTO PRESENTISMO ETEC', 1, 0, 0, 1, 110000, 110000, NOW() FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM codigo WHERE codigo_id = 984);

UPDATE codigo
SET nombre = 'DESCUENTO PRESENTISMO ETEC',
    docente = 1,
    no_docente = 0,
    transferible = 0,
    incluido_etec = 1,
    afip_concepto_sueldo_id_primer_semestre = 110000,
    afip_concepto_sueldo_id_segundo_semestre = 110000
WHERE codigo_id = 984;

INSERT INTO codigo_grupo (codigo_id, remunerativo, no_remunerativo, deduccion, total, created)
SELECT 984, 1, 0, 0, 0, NOW() FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM codigo_grupo WHERE codigo_id = 984);

UPDATE codigo_grupo
SET remunerativo = 1,
    no_remunerativo = 0,
    deduccion = 0,
    total = 0
WHERE codigo_id = 984;
