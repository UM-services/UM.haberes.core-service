package um.haberes.core.hexagonal.liquidaciones.bono.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.bono.application.exception.BonoException;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.BonoLote;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.in.ObtenerBonoLoteUseCase;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.BonoLoteDataPort;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.LiquidacionQueryPort;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in.DeleteZeroItemsByLegajoUseCase;

/**
 * Prepara el lote completo de bonos de una dependencia para que el reporte lo genere de una vez.
 *
 * <p>Replica el paso previo del flujo legajo por legajo (limpieza de items en cero) y después
 * delega la lectura en bloque. Los catálogos y el control del período se resuelven una sola vez.
 */
@Component
@RequiredArgsConstructor
public class ObtenerBonoLoteUseCaseImpl implements ObtenerBonoLoteUseCase {

    private final LiquidacionQueryPort liquidacionQueryPort;
    private final DeleteZeroItemsByLegajoUseCase deleteZeroItemsByLegajoUseCase;
    private final BonoLoteDataPort bonoLoteDataPort;

    @Override
    public BonoLote obtener(Integer dependenciaId, Integer anho, Integer mes, String salida) {
        List<Long> legajos = liquidacionQueryPort.findLegajoIdsByDependencia(dependenciaId, anho, mes,
                salida);
        if (legajos.isEmpty()) {
            throw new BonoException("No hay liquidaciones para la dependencia " + dependenciaId + " en "
                    + mes + "/" + anho);
        }
        legajos.forEach(legajoId -> deleteZeroItemsByLegajoUseCase.deleteZeroItemsByLegajo(legajoId,
                anho, mes));
        BonoLote lote = bonoLoteDataPort.cargar(legajos, anho, mes, dependenciaId, salida);
        if (lote.control() == null) {
            throw new BonoException("No hay control del período " + mes + "/" + anho);
        }
        return lote;
    }
}
