package um.haberes.core.hexagonal.liquidaciones.novedad.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.in.GetNovedadesByLegajoAndCodigoUseCase;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.out.NovedadRepository;

@Component
@RequiredArgsConstructor
public class GetNovedadesByLegajoAndCodigoUseCaseImpl implements GetNovedadesByLegajoAndCodigoUseCase {

    private final NovedadRepository novedadRepository;

    @Override
    public List<Novedad> getNovedadesByLegajoAndCodigo(Long legajoId, Integer anho, Integer mes, Integer codigoId) {
        return novedadRepository.findAllByLegajoIdAndAnhoAndMesAndCodigoId(legajoId, anho, mes, codigoId);
    }
}
