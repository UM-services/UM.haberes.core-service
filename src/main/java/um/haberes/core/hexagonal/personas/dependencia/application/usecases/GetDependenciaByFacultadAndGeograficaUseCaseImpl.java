package um.haberes.core.hexagonal.personas.dependencia.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.personas.dependencia.domain.model.Dependencia;
import um.haberes.core.hexagonal.personas.dependencia.domain.ports.in.GetDependenciaByFacultadAndGeograficaUseCase;
import um.haberes.core.hexagonal.personas.dependencia.domain.ports.out.DependenciaRepository;

@Component
@RequiredArgsConstructor
public class GetDependenciaByFacultadAndGeograficaUseCaseImpl implements GetDependenciaByFacultadAndGeograficaUseCase {

    private final DependenciaRepository dependenciaRepository;

    @Override
    public Optional<Dependencia> getDependenciaByFacultadAndGeografica(Integer facultadId, Integer geograficaId) {
        return dependenciaRepository.findFirstByFacultadIdAndGeograficaId(facultadId, geograficaId);
    }
}
