package um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.application.usecases;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.domain.ports.in.DeleteLegajoCodigoImputacionesByLegajoUseCase;
import um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.domain.ports.out.LegajoCodigoImputacionRepository;

@Component
@RequiredArgsConstructor
public class DeleteLegajoCodigoImputacionesByLegajoUseCaseImpl
        implements DeleteLegajoCodigoImputacionesByLegajoUseCase {

    private final LegajoCodigoImputacionRepository legajoCodigoImputacionRepository;

    @Override
    @Transactional
    public void deleteLegajoCodigoImputacionesByLegajo(Long legajoId, Integer anho, Integer mes) {
        legajoCodigoImputacionRepository.deleteAllByLegajo(legajoId, anho, mes);
    }
}
