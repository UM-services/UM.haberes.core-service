package um.haberes.core.hexagonal.liquidaciones.novedad.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.in.GetNovedadByIdUseCase;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.out.NovedadRepository;

@Component
@RequiredArgsConstructor
public class GetNovedadByIdUseCaseImpl implements GetNovedadByIdUseCase {

    private final NovedadRepository novedadRepository;

    @Override
    public Optional<Novedad> getNovedadById(Long novedadId) {
        return novedadRepository.findByNovedadId(novedadId);
    }
}
