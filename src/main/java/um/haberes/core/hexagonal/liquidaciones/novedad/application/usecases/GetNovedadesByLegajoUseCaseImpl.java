package um.haberes.core.hexagonal.liquidaciones.novedad.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.in.GetNovedadesByLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.out.NovedadRepository;

@Component
@RequiredArgsConstructor
public class GetNovedadesByLegajoUseCaseImpl implements GetNovedadesByLegajoUseCase {

    private final NovedadRepository novedadRepository;

    @Override
    public List<Novedad> getNovedadesByLegajo(Long legajoId, Integer anho, Integer mes) {
        return novedadRepository.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
    }
}
