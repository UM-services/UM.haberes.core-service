package um.haberes.core.hexagonal.liquidaciones.novedad.application.usecases;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.in.GetNovedadesByCodigosUseCase;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.out.NovedadRepository;

@Component
@RequiredArgsConstructor
public class GetNovedadesByCodigosUseCaseImpl implements GetNovedadesByCodigosUseCase {

    private final NovedadRepository novedadRepository;

    @Override
    public List<Novedad> getNovedadesByCodigos(Collection<Integer> codigoIds, Integer anho, Integer mes) {
        return novedadRepository.findAllByCodigoIdsAndAnhoAndMes(codigoIds, anho, mes);
    }
}
