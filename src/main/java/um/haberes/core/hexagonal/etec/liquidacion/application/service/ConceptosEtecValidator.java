package um.haberes.core.hexagonal.etec.liquidacion.application.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Verifica que todos los conceptos ETEC con importe distinto de cero tengan su código en el
 * catálogo. Sin esta validación el motor omite el concepto en silencio y persiste una
 * liquidación incompleta.
 */
public final class ConceptosEtecValidator {

    private ConceptosEtecValidator() {
    }

    public static void validar(Map<Integer, BigDecimal> conceptos, Set<Integer> codigosDisponibles) {
        List<Integer> faltantes = conceptos.entrySet().stream()
                .filter(concepto -> concepto.getValue() != null
                        && concepto.getValue().compareTo(BigDecimal.ZERO) != 0)
                .map(Map.Entry::getKey)
                .filter(codigoId -> !codigosDisponibles.contains(codigoId))
                .sorted()
                .toList();
        if (!faltantes.isEmpty()) {
            throw new IllegalStateException("Faltan códigos ETEC en el catálogo: " + faltantes
                    + ". Aplicar sql/010_codigos_etec.sql, sql/016_codigo_arraigo_etec.sql y "
                    + "sql/020_codigo_descuento_presentismo_etec.sql, y refrescar la caché de códigos.");
        }
    }
}
