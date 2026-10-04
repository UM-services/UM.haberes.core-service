package um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.application.usecases;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.domain.ports.in.DeleteLegajoContabilidadUseCase;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.domain.ports.out.LegajoContabilidadRepository;

@Component
@RequiredArgsConstructor
public class DeleteLegajoContabilidadUseCaseImpl implements DeleteLegajoContabilidadUseCase {

    private final LegajoContabilidadRepository legajoContabilidadRepository;

    @Override
    public void deleteLegajoContabilidad(Long legajoContabilidadId) {
        legajoContabilidadRepository.deleteById(legajoContabilidadId);
    }
}
