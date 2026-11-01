package um.haberes.core.hexagonal.cursos.designacion_tipo.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.designacion_tipo.domain.model.DesignacionTipo;
import um.haberes.core.hexagonal.cursos.designacion_tipo.domain.ports.in.GetDesignacionTipoByIdUseCase;
import um.haberes.core.hexagonal.cursos.designacion_tipo.domain.ports.out.DesignacionTipoRepository;

@Component
@RequiredArgsConstructor
public class GetDesignacionTipoByIdUseCaseImpl implements GetDesignacionTipoByIdUseCase {

    private final DesignacionTipoRepository designacionTipoRepository;

    @Override
    public Optional<DesignacionTipo> getDesignacionTipoById(Integer designacionTipoId) {
        return designacionTipoRepository.findByDesignacionTipoId(designacionTipoId);
    }
}
