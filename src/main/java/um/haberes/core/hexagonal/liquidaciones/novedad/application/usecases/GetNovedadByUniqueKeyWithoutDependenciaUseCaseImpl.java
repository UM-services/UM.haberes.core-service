package um.haberes.core.hexagonal.liquidaciones.novedad.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.in.GetNovedadByUniqueKeyWithoutDependenciaUseCase;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.out.NovedadRepository;

@Component
@RequiredArgsConstructor
public class GetNovedadByUniqueKeyWithoutDependenciaUseCaseImpl
        implements GetNovedadByUniqueKeyWithoutDependenciaUseCase {

    private final NovedadRepository novedadRepository;

    @Override
    public Optional<Novedad> getNovedadByUniqueKeyWithoutDependencia(Long legajoId, Integer anho, Integer mes,
            Integer codigoId) {
        return novedadRepository.findByLegajoIdAndAnhoAndMesAndCodigoIdAndDependenciaIdIsNull(legajoId, anho, mes,
                codigoId);
    }
}
