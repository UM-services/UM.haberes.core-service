package um.haberes.core.hexagonal.liquidaciones.letra.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.letra.domain.model.Letra;
import um.haberes.core.hexagonal.liquidaciones.letra.domain.ports.in.GetLetraByUniqueKeyUseCase;
import um.haberes.core.hexagonal.liquidaciones.letra.domain.ports.out.LetraRepository;

@Component
@RequiredArgsConstructor
public class GetLetraByUniqueKeyUseCaseImpl implements GetLetraByUniqueKeyUseCase {

    private final LetraRepository letraRepository;

    @Override
    public Optional<Letra> getLetraByUniqueKey(Long legajoId, Integer anho, Integer mes) {
        return letraRepository.findByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
    }
}
