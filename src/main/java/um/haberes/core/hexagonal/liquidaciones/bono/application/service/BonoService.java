package um.haberes.core.hexagonal.liquidaciones.bono.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.Actividad;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.AuditoriaBono;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.BonoImpresion;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.EnvioBono;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.IntegridadBono;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.PeriodoBono;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.in.ObtenerHistorialAuditoriaBonoUseCase;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.in.PrepararBonoUseCase;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.in.PrepararEnvioBonoUseCase;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.in.PrintPrepareBonoUseCase;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.in.RegistrarAuditoriaBonoUseCase;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.in.VerificarIntegridadBonoUseCase;

@Service
@RequiredArgsConstructor
public class BonoService {

    private final VerificarIntegridadBonoUseCase verificarIntegridadBonoUseCase;
    private final PrepararBonoUseCase prepararBonoUseCase;
    private final RegistrarAuditoriaBonoUseCase registrarAuditoriaBonoUseCase;
    private final PrintPrepareBonoUseCase printPrepareBonoUseCase;
    private final PrepararEnvioBonoUseCase prepararEnvioBonoUseCase;
    private final ObtenerHistorialAuditoriaBonoUseCase obtenerHistorialAuditoriaBonoUseCase;

    public IntegridadBono verificarIntegridad(PeriodoBono periodo) {
        return verificarIntegridadBonoUseCase.verificarIntegridad(periodo);
    }

    public Actividad preparar(PeriodoBono periodo) {
        return prepararBonoUseCase.preparar(periodo);
    }

    public BonoImpresion registrarAuditoria(AuditoriaBono auditoria) {
        return registrarAuditoriaBonoUseCase.registrarAuditoria(auditoria);
    }

    public BonoImpresion printPrepare(AuditoriaBono auditoria) {
        return printPrepareBonoUseCase.printPrepare(auditoria);
    }

    public BonoImpresion prepararEnvio(EnvioBono envio) {
        return prepararEnvioBonoUseCase.prepararEnvio(envio);
    }

    public List<BonoImpresion> getHistorial(PeriodoBono periodo) {
        return obtenerHistorialAuditoriaBonoUseCase.getHistorial(periodo);
    }
}
