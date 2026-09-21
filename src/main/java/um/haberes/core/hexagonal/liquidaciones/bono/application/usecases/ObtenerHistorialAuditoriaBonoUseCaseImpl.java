package um.haberes.core.hexagonal.liquidaciones.bono.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.BonoImpresion;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.PeriodoBono;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.in.ObtenerHistorialAuditoriaBonoUseCase;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.BonoImpresionRepository;

@Component
@RequiredArgsConstructor
public class ObtenerHistorialAuditoriaBonoUseCaseImpl implements ObtenerHistorialAuditoriaBonoUseCase {

    private final BonoImpresionRepository bonoImpresionRepository;

    @Override
    public List<BonoImpresion> getHistorial(PeriodoBono periodo) {
        return bonoImpresionRepository.findAllByLegajoIdAndAnhoAndMesOrderByFechaDesc(periodo.getLegajoId(),
                periodo.getAnho(), periodo.getMes());
    }
}
