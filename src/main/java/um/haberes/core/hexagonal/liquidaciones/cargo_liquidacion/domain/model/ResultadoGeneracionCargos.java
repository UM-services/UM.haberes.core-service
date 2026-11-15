package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model;

import java.util.List;
import java.util.Map;

/**
 * Resultado de una generación de cargos: las filas guardadas (o previsualizadas) y cuántas
 * aportó cada universo.
 */
public record ResultadoGeneracionCargos(List<CargoLiquidacion> cargos, Map<UniversoCargos, Integer> conteos) {

    public int total() {
        return cargos == null ? 0 : cargos.size();
    }

    public int conteo(UniversoCargos universo) {
        return conteos == null ? 0 : conteos.getOrDefault(universo, 0);
    }
}
