package um.haberes.core.hexagonal.liquidaciones.novedad.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.in.GetNovedadesByPeriodoUseCase;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.out.NovedadRepository;

@Component
@RequiredArgsConstructor
public class GetNovedadesByPeriodoUseCaseImpl implements GetNovedadesByPeriodoUseCase {

    private final NovedadRepository novedadRepository;

    @Override
    public List<Novedad> getNovedadesByPeriodo(Integer anho, Integer mes) {
        return novedadRepository.findAllByAnhoAndMes(anho, mes);
    }
}
