package um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.application.usecases;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.domain.model.LegajoContabilidad;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.domain.ports.in.CreateLegajoContabilidadUseCase;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.domain.ports.out.LegajoContabilidadRepository;

@Component
@RequiredArgsConstructor
public class CreateLegajoContabilidadUseCaseImpl implements CreateLegajoContabilidadUseCase {

    private final LegajoContabilidadRepository legajoContabilidadRepository;

    @Override
    public LegajoContabilidad createLegajoContabilidad(LegajoContabilidad legajoContabilidad) {
        return legajoContabilidadRepository.create(legajoContabilidad);
    }
}
