package um.haberes.core.hexagonal.geografica.application.usecases;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import um.haberes.core.hexagonal.geografica.domain.model.Geografica;
import um.haberes.core.hexagonal.geografica.domain.ports.in.GetGeograficaByIdUseCase;
import um.haberes.core.hexagonal.geografica.domain.ports.out.GeograficaRepository;
import java.util.Optional;
@Component
@RequiredArgsConstructor
public class GetGeograficaByIdUseCaseImpl implements GetGeograficaByIdUseCase {
    private final GeograficaRepository repository;
    @Override public Geografica getById(Integer id) { return repository.findById(id); }
}
