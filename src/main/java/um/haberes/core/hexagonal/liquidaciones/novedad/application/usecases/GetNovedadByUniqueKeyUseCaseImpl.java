package um.haberes.core.hexagonal.liquidaciones.novedad.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.in.GetNovedadByUniqueKeyUseCase;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.out.NovedadRepository;

@Component
@RequiredArgsConstructor
public class GetNovedadByUniqueKeyUseCaseImpl implements GetNovedadByUniqueKeyUseCase {

    private final NovedadRepository novedadRepository;

    @Override
    public Optional<Novedad> getNovedadByUniqueKey(Long legajoId, Integer anho, Integer mes, Integer codigoId,
            Integer dependenciaId) {
        return novedadRepository.findByLegajoIdAndAnhoAndMesAndCodigoIdAndDependenciaId(legajoId, anho, mes, codigoId,
                dependenciaId);
    }
}
