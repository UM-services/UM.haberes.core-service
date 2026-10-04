package um.haberes.core.hexagonal.personas.dependencia.application.usecases;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.personas.dependencia.domain.model.Dependencia;
import um.haberes.core.hexagonal.personas.dependencia.domain.ports.in.GetDependenciasByIdsUseCase;
import um.haberes.core.hexagonal.personas.dependencia.domain.ports.out.DependenciaRepository;

@Component
@RequiredArgsConstructor
public class GetDependenciasByIdsUseCaseImpl implements GetDependenciasByIdsUseCase {

    private final DependenciaRepository dependenciaRepository;

    @Override
    public List<Dependencia> getDependenciasByIds(Set<Integer> dependenciaIds) {
        return dependenciaRepository.findAllByDependenciaIdIn(dependenciaIds);
    }
}
