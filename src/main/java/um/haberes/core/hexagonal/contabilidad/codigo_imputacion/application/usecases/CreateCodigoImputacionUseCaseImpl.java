package um.haberes.core.hexagonal.contabilidad.codigo_imputacion.application.usecases;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.domain.model.CodigoImputacion;
import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.domain.ports.in.CreateCodigoImputacionUseCase;
import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.domain.ports.out.CodigoImputacionRepository;

@Component
@RequiredArgsConstructor
public class CreateCodigoImputacionUseCaseImpl implements CreateCodigoImputacionUseCase {

    private final CodigoImputacionRepository codigoImputacionRepository;

    @Override
    public CodigoImputacion createCodigoImputacion(CodigoImputacion codigoImputacion) {
        return codigoImputacionRepository.create(codigoImputacion);
    }
}
