package um.haberes.core.hexagonal.cursos.designacion_tipo.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.designacion_tipo.domain.model.DesignacionTipo;
import um.haberes.core.hexagonal.cursos.designacion_tipo.domain.ports.in.GetAllDesignacionTiposUseCase;
import um.haberes.core.hexagonal.cursos.designacion_tipo.domain.ports.out.DesignacionTipoRepository;

@Component
@RequiredArgsConstructor
public class GetAllDesignacionTiposUseCaseImpl implements GetAllDesignacionTiposUseCase {

    private final DesignacionTipoRepository designacionTipoRepository;

    @Override
    public List<DesignacionTipo> getAllDesignacionTipos() {
        return designacionTipoRepository.findAll();
    }
}
