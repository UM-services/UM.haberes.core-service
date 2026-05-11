package um.haberes.core.hexagonal.geografica.application.usecases;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import um.haberes.core.hexagonal.geografica.domain.model.Geografica;
import um.haberes.core.hexagonal.geografica.domain.ports.in.GetAllGeograficasUseCase;
import um.haberes.core.hexagonal.geografica.domain.ports.out.GeograficaRepository;
import java.util.List;
@Component
@RequiredArgsConstructor
public class GetAllGeograficasUseCaseImpl implements GetAllGeograficasUseCase {
    private final GeograficaRepository repository;
    @Override public List<Geografica> getAll() { return repository.findAll(); }
}
