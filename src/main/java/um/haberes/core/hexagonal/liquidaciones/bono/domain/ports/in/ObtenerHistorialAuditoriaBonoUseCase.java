package um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.BonoImpresion;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.PeriodoBono;

public interface ObtenerHistorialAuditoriaBonoUseCase {

    List<BonoImpresion> getHistorial(PeriodoBono periodo);
}
