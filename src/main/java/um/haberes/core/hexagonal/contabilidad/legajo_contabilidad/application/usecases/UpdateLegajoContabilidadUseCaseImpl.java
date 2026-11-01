package um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.domain.model.LegajoContabilidad;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.domain.ports.in.UpdateLegajoContabilidadUseCase;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.domain.ports.out.LegajoContabilidadRepository;

@Component
@RequiredArgsConstructor
public class UpdateLegajoContabilidadUseCaseImpl implements UpdateLegajoContabilidadUseCase {

    private final LegajoContabilidadRepository legajoContabilidadRepository;

    @Override
    public Optional<LegajoContabilidad> updateLegajoContabilidad(Long legajoContabilidadId,
            LegajoContabilidad legajoContabilidad) {
        return legajoContabilidadRepository.update(legajoContabilidadId, legajoContabilidad);
    }
}
