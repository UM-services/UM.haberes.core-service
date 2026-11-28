-- Adicional Directivo ETEC.
--
-- El concepto ya no se crea con un `codigo_id` fijo. El código se crea o se elige en la
-- pantalla de Códigos y el período lo referencia en `control_etec.codigo_adicional_directivo`
-- (ver `sql/031_control_etec_codigo_adicional_directivo.sql`).
--
-- Este script se conserva como marcador histórico: no inserta ningún código.

SELECT 'Adicional Directivo ETEC: el código se configura en control_etec.codigo_adicional_directivo' AS resultado;
