package um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.application.usecases;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.domain.model.LegajoCodigoImputacion;
import um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.domain.ports.in.CreateLegajoCodigoImputacionUseCase;
import um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.domain.ports.out.LegajoCodigoImputacionRepository;

@Component
@RequiredArgsConstructor
public class CreateLegajoCodigoImputacionUseCaseImpl implements CreateLegajoCodigoImputacionUseCase {

    private final LegajoCodigoImputacionRepository legajoCodigoImputacionRepository;

    @Override
    public LegajoCodigoImputacion createLegajoCodigoImputacion(LegajoCodigoImputacion legajoCodigoImputacion) {
        return legajoCodigoImputacionRepository.create(legajoCodigoImputacion);
    }
}
