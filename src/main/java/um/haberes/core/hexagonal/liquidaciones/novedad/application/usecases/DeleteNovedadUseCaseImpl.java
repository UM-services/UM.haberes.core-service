package um.haberes.core.hexagonal.liquidaciones.novedad.application.usecases;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.in.DeleteNovedadUseCase;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.out.NovedadRepository;

@Component
@RequiredArgsConstructor
public class DeleteNovedadUseCaseImpl implements DeleteNovedadUseCase {

    private final NovedadRepository novedadRepository;

    @Transactional
    @Override
    public void deleteNovedad(Long novedadId) {
        novedadRepository.deleteByNovedadId(novedadId);
    }
}
