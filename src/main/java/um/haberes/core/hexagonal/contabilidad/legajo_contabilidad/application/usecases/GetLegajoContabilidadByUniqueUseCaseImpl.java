package um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.domain.model.LegajoContabilidad;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.domain.ports.in.GetLegajoContabilidadByUniqueUseCase;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.domain.ports.out.LegajoContabilidadRepository;

@Component
@RequiredArgsConstructor
public class GetLegajoContabilidadByUniqueUseCaseImpl implements GetLegajoContabilidadByUniqueUseCase {

    private final LegajoContabilidadRepository legajoContabilidadRepository;

    @Override
    public Optional<LegajoContabilidad> getLegajoContabilidadByUnique(Long legajoId, Integer anho, Integer mes) {
        return legajoContabilidadRepository.findByUnique(legajoId, anho, mes);
    }
}
