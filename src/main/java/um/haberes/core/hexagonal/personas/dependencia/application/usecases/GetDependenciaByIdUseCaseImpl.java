package um.haberes.core.hexagonal.personas.dependencia.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.personas.dependencia.domain.model.Dependencia;
import um.haberes.core.hexagonal.personas.dependencia.domain.ports.in.GetDependenciaByIdUseCase;
import um.haberes.core.hexagonal.personas.dependencia.domain.ports.out.DependenciaRepository;

@Component
@RequiredArgsConstructor
public class GetDependenciaByIdUseCaseImpl implements GetDependenciaByIdUseCase {

    private final DependenciaRepository dependenciaRepository;

    @Override
    public Optional<Dependencia> getDependenciaById(Integer dependenciaId) {
        return dependenciaRepository.findByDependenciaId(dependenciaId);
    }
}
