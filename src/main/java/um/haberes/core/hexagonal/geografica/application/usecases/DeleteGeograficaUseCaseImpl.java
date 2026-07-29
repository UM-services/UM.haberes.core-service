package um.haberes.core.hexagonal.geografica.application.usecases;

import um.haberes.core.hexagonal.geografica.domain.ports.in.DeleteGeograficaUseCase;
import um.haberes.core.hexagonal.geografica.domain.ports.out.GeograficaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteGeograficaUseCaseImpl implements DeleteGeograficaUseCase {

    private final GeograficaRepository geograficaRepository;

    @Override
    public boolean deleteGeografica(Integer id) {
        return geograficaRepository.deleteById(id);
    }
}
