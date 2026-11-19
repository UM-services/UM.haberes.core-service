-- Monto fijo del cargo ETEC del período 2026/11.
--
-- El valor del cargo por puntos se calcula como `puntos x valor_punto_etec + monto_fijo_cargo`.
-- El monto fijo es un parámetro del período: se carga acá y los períodos siguientes lo heredan
-- con `POST /api/haberes/core/etec/control/copiar/{anho}/{mes}`.
--
-- Idempotente: deja el período en el importe informado, sin tocar los demás parámetros ni otros
-- períodos.

UPDATE control_etec
SET monto_fijo_cargo = 247543.34,
    updated = NOW(6)
WHERE anho = 2026
  AND mes = 11;
