package um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.in;

import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.IntegridadBono;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.PeriodoBono;

public interface VerificarIntegridadBonoUseCase {

    IntegridadBono verificarIntegridad(PeriodoBono periodo);
}
