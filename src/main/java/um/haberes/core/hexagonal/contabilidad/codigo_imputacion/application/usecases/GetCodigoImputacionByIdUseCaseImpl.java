package um.haberes.core.hexagonal.contabilidad.codigo_imputacion.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.domain.model.CodigoImputacion;
import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.domain.ports.in.GetCodigoImputacionByIdUseCase;
import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.domain.ports.out.CodigoImputacionRepository;

@Component
@RequiredArgsConstructor
public class GetCodigoImputacionByIdUseCaseImpl implements GetCodigoImputacionByIdUseCase {

    private final CodigoImputacionRepository codigoImputacionRepository;

    @Override
    public Optional<CodigoImputacion> getCodigoImputacionById(Long codigoImputacionId) {
        return codigoImputacionRepository.findById(codigoImputacionId);
    }
}
