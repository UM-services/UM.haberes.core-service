package um.haberes.core.hexagonal.liquidaciones.novedad.application.usecases;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.in.DeleteNovedadesByPeriodoUseCase;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.out.NovedadRepository;

@Component
@RequiredArgsConstructor
public class DeleteNovedadesByPeriodoUseCaseImpl implements DeleteNovedadesByPeriodoUseCase {

    private final NovedadRepository novedadRepository;

    @Transactional
    @Override
    public void deleteNovedadesByPeriodo(Integer anho, Integer mes) {
        novedadRepository.deleteAllByAnhoAndMes(anho, mes);
    }
}
