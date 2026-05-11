package um.haberes.core.hexagonal.facultad.application.usecases;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import um.haberes.core.hexagonal.facultad.domain.model.Facultad;
import um.haberes.core.hexagonal.facultad.domain.ports.in.GetFacultadesFiltradasUseCase;
import um.haberes.core.hexagonal.facultad.domain.ports.out.FacultadRepository;
import java.util.List;
@Component
@RequiredArgsConstructor
public class GetFacultadesFiltradasUseCaseImpl implements GetFacultadesFiltradasUseCase {
    private final FacultadRepository repository;
    @Override 
    public List<Facultad> getFacultadesFiltradas() { 
        List<Integer> codigos = List.of(1, 2, 3, 4, 5, 14, 15);
        return repository.findAllIn(codigos);
    }
}
