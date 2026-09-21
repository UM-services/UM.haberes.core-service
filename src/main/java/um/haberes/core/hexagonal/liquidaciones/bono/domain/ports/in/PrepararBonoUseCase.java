package um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.in;

import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.Actividad;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.PeriodoBono;

public interface PrepararBonoUseCase {

    Actividad preparar(PeriodoBono periodo);
}
