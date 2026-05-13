package um.haberes.core.hexagonal.geografica.application.usecases;

import um.haberes.core.hexagonal.geografica.domain.model.Geografica;
import um.haberes.core.hexagonal.geografica.domain.ports.in.GetGeograficaByIdUseCase;
import um.haberes.core.hexagonal.geografica.domain.ports.out.GeograficaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GetGeograficaByIdUseCaseImpl implements GetGeograficaByIdUseCase {

    private final GeograficaRepository geograficaRepository;

    @Override
    public Optional<Geografica> getGeograficaById(Integer id) {
        return geograficaRepository.findById(id);
    }
}
