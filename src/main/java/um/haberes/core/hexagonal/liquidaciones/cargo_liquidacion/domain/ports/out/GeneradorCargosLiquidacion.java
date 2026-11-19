package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.ContextoGeneracionCargos;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.UniversoCargos;

/**
 * Fuente de cargos de liquidación. Cada implementación aporta las filas de un universo y
 * declara si participa del contexto actual.
 *
 * <p>El orquestador no conoce las fuentes: inyecta la lista de implementaciones, descarta las
 * que no aplican y delega el cálculo. Agregar una fuente nueva es agregar un {@code @Component},
 * sin tocar el orquestador ni los controllers.</p>
 */
public interface GeneradorCargosLiquidacion {

    /** Universo al que pertenecen las filas generadas. Define el alcance de borrado. */
    UniversoCargos universo();

    /**
     * Indica si la fuente participa del contexto. Por defecto participa siempre que su universo
     * haya sido solicitado; las fuentes que dependen del modo de liquidación o del alcance
     * (legajo/período) lo resuelven acá.
     */
    default boolean aplica(ContextoGeneracionCargos contexto) {
        return true;
    }

    /** Filas que la fuente aporta para el contexto. Nunca escribe en la base. */
    List<CargoLiquidacion> cargos(ContextoGeneracionCargos contexto);
}
