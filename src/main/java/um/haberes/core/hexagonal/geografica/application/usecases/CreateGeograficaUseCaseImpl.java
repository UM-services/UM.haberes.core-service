package um.haberes.core.hexagonal.geografica.application.usecases;

import um.haberes.core.hexagonal.geografica.domain.model.Geografica;
import um.haberes.core.hexagonal.geografica.domain.ports.in.CreateGeograficaUseCase;
import um.haberes.core.hexagonal.geografica.domain.ports.out.GeograficaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateGeograficaUseCaseImpl implements CreateGeograficaUseCase {

    private final GeograficaRepository geograficaRepository;

    @Override
    public Geografica createGeografica(Geografica geografica) {
        return geograficaRepository.create(geografica);
    }
}
