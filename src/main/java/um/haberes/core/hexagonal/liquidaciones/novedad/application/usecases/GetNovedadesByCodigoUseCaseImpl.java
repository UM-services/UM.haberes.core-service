package um.haberes.core.hexagonal.liquidaciones.novedad.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.in.GetNovedadesByCodigoUseCase;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.out.NovedadRepository;

@Component
@RequiredArgsConstructor
public class GetNovedadesByCodigoUseCaseImpl implements GetNovedadesByCodigoUseCase {

    private final NovedadRepository novedadRepository;

    @Override
    public List<Novedad> getNovedadesByCodigo(Integer codigoId, Integer anho, Integer mes) {
        return novedadRepository.findAllByCodigoIdAndAnhoAndMes(codigoId, anho, mes);
    }
}
