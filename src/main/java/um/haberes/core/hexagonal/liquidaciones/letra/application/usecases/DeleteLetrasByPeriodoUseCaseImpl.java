package um.haberes.core.hexagonal.liquidaciones.letra.application.usecases;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.letra.domain.ports.in.DeleteLetrasByPeriodoUseCase;
import um.haberes.core.hexagonal.liquidaciones.letra.domain.ports.out.LetraRepository;

@Component
@RequiredArgsConstructor
public class DeleteLetrasByPeriodoUseCaseImpl implements DeleteLetrasByPeriodoUseCase {

    private final LetraRepository letraRepository;

    @Transactional
    @Override
    public void deleteLetrasByPeriodo(Integer anho, Integer mes) {
        letraRepository.deleteAllByAnhoAndMes(anho, mes);
    }
}
