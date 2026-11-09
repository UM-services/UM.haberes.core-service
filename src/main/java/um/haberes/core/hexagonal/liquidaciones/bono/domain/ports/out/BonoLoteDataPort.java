package um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.BonoLote;

/** Lectura en bloque de todo lo que necesita el lote de bonos de una dependencia. */
public interface BonoLoteDataPort {

    BonoLote cargar(List<Long> legajoIds, Integer anho, Integer mes, Integer dependenciaId,
                    String salida);
}
