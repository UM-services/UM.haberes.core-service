package um.haberes.core.hexagonal.liquidaciones.letra.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.letra.domain.model.Letra;
import um.haberes.core.hexagonal.liquidaciones.letra.domain.ports.in.GetLetrasByPeriodoUseCase;
import um.haberes.core.hexagonal.liquidaciones.letra.domain.ports.out.LetraRepository;

@Component
@RequiredArgsConstructor
public class GetLetrasByPeriodoUseCaseImpl implements GetLetrasByPeriodoUseCase {

    private final LetraRepository letraRepository;

    @Override
    public List<Letra> getLetrasByPeriodo(Integer anho, Integer mes, Integer limit) {
        return letraRepository.findAllByAnhoAndMes(anho, mes, limit);
    }
}
