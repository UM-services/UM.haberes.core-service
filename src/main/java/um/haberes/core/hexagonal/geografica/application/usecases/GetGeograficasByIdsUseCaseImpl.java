package um.haberes.core.hexagonal.geografica.application.usecases;

import um.haberes.core.hexagonal.geografica.domain.model.Geografica;
import um.haberes.core.hexagonal.geografica.domain.ports.in.GetGeograficasByIdsUseCase;
import um.haberes.core.hexagonal.geografica.domain.ports.out.GeograficaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetGeograficasByIdsUseCaseImpl implements GetGeograficasByIdsUseCase {

    private final GeograficaRepository geograficaRepository;

    @Override
    public List<Geografica> getGeograficasByIds(List<Integer> ids) {
        return geograficaRepository.findAllIn(ids);
    }
}
