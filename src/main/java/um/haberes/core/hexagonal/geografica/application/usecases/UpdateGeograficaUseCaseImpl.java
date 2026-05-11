package um.haberes.core.hexagonal.geografica.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import um.haberes.core.hexagonal.geografica.domain.model.Geografica;
import um.haberes.core.hexagonal.geografica.domain.ports.in.UpdateGeograficaUseCase;
import um.haberes.core.hexagonal.geografica.domain.ports.out.GeograficaRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UpdateGeograficaUseCaseImpl implements UpdateGeograficaUseCase {

    private final GeograficaRepository geograficaRepository;

    @Override
    public Geografica update(Integer id, Geografica geografica) {
        return geograficaRepository.update(id, geografica);
    }
}
