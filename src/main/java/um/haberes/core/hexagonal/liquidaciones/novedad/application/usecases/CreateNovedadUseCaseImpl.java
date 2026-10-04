package um.haberes.core.hexagonal.liquidaciones.novedad.application.usecases;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.in.CreateNovedadUseCase;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.out.NovedadRepository;

@Component
@RequiredArgsConstructor
public class CreateNovedadUseCaseImpl implements CreateNovedadUseCase {

    private final NovedadRepository novedadRepository;

    @Transactional
    @Override
    public Novedad createNovedad(Novedad novedad) {
        return novedadRepository.save(novedad);
    }
}
