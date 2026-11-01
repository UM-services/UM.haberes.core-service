package um.haberes.core.hexagonal.liquidaciones.bono.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.bono.application.exception.BonoException;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.AuditoriaBono;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.BonoImpresion;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.FaltanteBono;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.IntegridadBono;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.PeriodoBono;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.in.PrintPrepareBonoUseCase;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.in.PrepararBonoUseCase;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.in.RegistrarAuditoriaBonoUseCase;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.in.VerificarIntegridadBonoUseCase;

@Component
@RequiredArgsConstructor
public class PrintPrepareBonoUseCaseImpl implements PrintPrepareBonoUseCase {

    private final VerificarIntegridadBonoUseCase verificarIntegridadBonoUseCase;
    private final PrepararBonoUseCase prepararBonoUseCase;
    private final RegistrarAuditoriaBonoUseCase registrarAuditoriaBonoUseCase;

    @Override
    @Transactional
    public BonoImpresion printPrepare(AuditoriaBono auditoria) {
        PeriodoBono periodo = PeriodoBono.builder()
                .legajoId(auditoria.getLegajoId())
                .anho(auditoria.getAnho())
                .mes(auditoria.getMes())
                .build();
        IntegridadBono integridad = verificarIntegridadBonoUseCase.verificarIntegridad(periodo);
        if (!integridad.isOk()) {
            List<FaltanteBono> faltantes = integridad.getFaltantes();
            throw new BonoException("Cannot print Bono: integridad fallida, faltantes " + faltantes, faltantes);
        }
        prepararBonoUseCase.preparar(periodo);
        return registrarAuditoriaBonoUseCase.registrarAuditoria(auditoria);
    }
}
