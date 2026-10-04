package um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.domain.model.LegajoCodigoImputacion;
import um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.domain.ports.in.FindLegajoCodigoImputacionesByLegajoUseCase;
import um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.domain.ports.out.LegajoCodigoImputacionRepository;

@Component
@RequiredArgsConstructor
public class FindLegajoCodigoImputacionesByLegajoUseCaseImpl
        implements FindLegajoCodigoImputacionesByLegajoUseCase {

    private final LegajoCodigoImputacionRepository legajoCodigoImputacionRepository;

    @Override
    public List<LegajoCodigoImputacion> findLegajoCodigoImputacionesByLegajo(Long legajoId, Integer anho,
            Integer mes) {
        return legajoCodigoImputacionRepository.findByLegajo(legajoId, anho, mes);
    }
}
