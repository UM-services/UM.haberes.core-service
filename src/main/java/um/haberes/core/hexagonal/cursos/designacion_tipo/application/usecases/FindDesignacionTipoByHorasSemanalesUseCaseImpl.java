package um.haberes.core.hexagonal.cursos.designacion_tipo.application.usecases;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.designacion_tipo.domain.model.DesignacionTipo;
import um.haberes.core.hexagonal.cursos.designacion_tipo.domain.ports.in.FindDesignacionTipoByHorasSemanalesUseCase;
import um.haberes.core.hexagonal.cursos.designacion_tipo.domain.ports.out.DesignacionTipoRepository;

@Component
@RequiredArgsConstructor
public class FindDesignacionTipoByHorasSemanalesUseCaseImpl implements FindDesignacionTipoByHorasSemanalesUseCase {

    private final DesignacionTipoRepository designacionTipoRepository;

    @Override
    public Optional<DesignacionTipo> findDesignacionTipoByHorasSemanales(BigDecimal horasSemanales) {
        return designacionTipoRepository.findFirstByHorasSemanalesGreaterThanEqual(horasSemanales);
    }
}
