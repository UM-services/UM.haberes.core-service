package um.haberes.core.hexagonal.liquidaciones.letra.application.usecases;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.letra.domain.ports.in.DeleteLetraByUniqueKeyUseCase;
import um.haberes.core.hexagonal.liquidaciones.letra.domain.ports.out.LetraRepository;

@Component
@RequiredArgsConstructor
public class DeleteLetraByUniqueKeyUseCaseImpl implements DeleteLetraByUniqueKeyUseCase {

    private final LetraRepository letraRepository;

    @Transactional
    @Override
    public void deleteLetraByUniqueKey(Long legajoId, Integer anho, Integer mes) {
        letraRepository.deleteAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
    }
}
