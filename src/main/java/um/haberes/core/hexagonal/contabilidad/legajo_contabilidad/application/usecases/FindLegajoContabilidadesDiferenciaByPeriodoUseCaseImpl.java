package um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.domain.model.LegajoContabilidad;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.domain.ports.in.FindLegajoContabilidadesDiferenciaByPeriodoUseCase;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.domain.ports.out.LegajoContabilidadRepository;

@Component
@RequiredArgsConstructor
public class FindLegajoContabilidadesDiferenciaByPeriodoUseCaseImpl
        implements FindLegajoContabilidadesDiferenciaByPeriodoUseCase {

    private final LegajoContabilidadRepository legajoContabilidadRepository;

    @Override
    public List<LegajoContabilidad> findLegajoContabilidadesDiferenciaByPeriodo(Integer anho, Integer mes) {
        return legajoContabilidadRepository.findAllDiferenciaByPeriodo(anho, mes);
    }
}
