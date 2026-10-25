package um.haberes.core.hexagonal.liquidaciones.bono.application.usecases;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.bono.application.exception.BonoException;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.AuditoriaBono;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.ResumenPreparacionBonos;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.in.PrepararBonosDependenciaUseCase;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.in.PrintPrepareBonoUseCase;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.LiquidacionQueryPort;

/**
 * Equivalente masivo del {@code beforePrintBono} del cliente anterior: recorre las liquidaciones
 * de la dependencia y, por cada legajo, verifica integridad, recalcula actividad y registra la
 * auditoría. Los fallos se acumulan para que la pantalla avise cuáles no se pudieron preparar.
 */
@Component
@RequiredArgsConstructor
public class PrepararBonosDependenciaUseCaseImpl implements PrepararBonosDependenciaUseCase {

    private final LiquidacionQueryPort liquidacionQueryPort;
    private final PrintPrepareBonoUseCase printPrepareBonoUseCase;

    @Override
    public ResumenPreparacionBonos preparar(Integer dependenciaId, Integer anho, Integer mes,
                                            String salida, Long legajoIdSolicitud,
                                            String ipAddress) {
        List<Long> legajos = liquidacionQueryPort
                .findLegajoIdsByDependencia(dependenciaId, anho, mes, salida);
        List<ResumenPreparacionBonos.Fallido> fallidos = new ArrayList<>();
        int preparados = 0;
        for (Long legajoId : legajos) {
            try {
                printPrepareBonoUseCase.printPrepare(AuditoriaBono.builder()
                        .legajoId(legajoId)
                        .anho(anho)
                        .mes(mes)
                        .legajoIdSolicitud(legajoIdSolicitud)
                        .ipAddress(ipAddress)
                        .build());
                preparados++;
            } catch (BonoException e) {
                fallidos.add(new ResumenPreparacionBonos.Fallido(legajoId, e.getMessage()));
            }
        }
        return new ResumenPreparacionBonos(dependenciaId, anho, mes, salida, legajos.size(),
                preparados, fallidos);
    }
}
