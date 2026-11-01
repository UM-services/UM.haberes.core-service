package um.haberes.core.hexagonal.liquidaciones.letra.application.usecases;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.letra.domain.model.Letra;
import um.haberes.core.hexagonal.liquidaciones.letra.domain.ports.in.CreateLetraUseCase;
import um.haberes.core.hexagonal.liquidaciones.letra.domain.ports.out.LetraRepository;

@Component
@RequiredArgsConstructor
public class CreateLetraUseCaseImpl implements CreateLetraUseCase {

    private final LetraRepository letraRepository;

    @Transactional
    @Override
    public Letra createLetra(Letra letra) {
        return letraRepository.save(letra);
    }
}
