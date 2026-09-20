package um.haberes.core.hexagonal.personas.dependencia.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.personas.dependencia.domain.model.Dependencia;
import um.haberes.core.hexagonal.personas.dependencia.domain.ports.in.GetAllDependenciasUseCase;
import um.haberes.core.hexagonal.personas.dependencia.domain.ports.out.DependenciaRepository;

@Component
@RequiredArgsConstructor
public class GetAllDependenciasUseCaseImpl implements GetAllDependenciasUseCase {

    private final DependenciaRepository dependenciaRepository;

    @Override
    public List<Dependencia> getAllDependencias() {
        return dependenciaRepository.findAll();
    }
}
