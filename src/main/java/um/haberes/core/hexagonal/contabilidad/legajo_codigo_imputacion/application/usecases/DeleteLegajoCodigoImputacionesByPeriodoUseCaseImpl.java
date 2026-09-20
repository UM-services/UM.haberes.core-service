package um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.application.usecases;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.domain.ports.in.DeleteLegajoCodigoImputacionesByPeriodoUseCase;
import um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.domain.ports.out.LegajoCodigoImputacionRepository;

@Component
@RequiredArgsConstructor
public class DeleteLegajoCodigoImputacionesByPeriodoUseCaseImpl
        implements DeleteLegajoCodigoImputacionesByPeriodoUseCase {

    private final LegajoCodigoImputacionRepository legajoCodigoImputacionRepository;

    @Override
    @Transactional
    public void deleteLegajoCodigoImputacionesByPeriodo(Integer anho, Integer mes) {
        legajoCodigoImputacionRepository.deleteAllByPeriodo(anho, mes);
    }
}
