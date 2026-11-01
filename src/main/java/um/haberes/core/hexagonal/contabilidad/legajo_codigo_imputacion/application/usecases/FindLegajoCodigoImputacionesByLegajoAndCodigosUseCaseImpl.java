package um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.domain.model.LegajoCodigoImputacion;
import um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.domain.ports.in.FindLegajoCodigoImputacionesByLegajoAndCodigosUseCase;
import um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.domain.ports.out.LegajoCodigoImputacionRepository;

@Component
@RequiredArgsConstructor
public class FindLegajoCodigoImputacionesByLegajoAndCodigosUseCaseImpl
        implements FindLegajoCodigoImputacionesByLegajoAndCodigosUseCase {

    private final LegajoCodigoImputacionRepository legajoCodigoImputacionRepository;

    @Override
    public List<LegajoCodigoImputacion> findLegajoCodigoImputacionesByLegajoAndCodigos(Long legajoId, Integer anho,
            Integer mes, List<Integer> codigoIds) {
        return legajoCodigoImputacionRepository.findByLegajoAndCodigos(legajoId, anho, mes, codigoIds);
    }
}
