package um.haberes.core.hexagonal.contabilidad.codigo_imputacion.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.domain.model.CodigoImputacion;
import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.domain.ports.in.GetAllCodigoImputacionesUseCase;
import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.domain.ports.out.CodigoImputacionRepository;

@Component
@RequiredArgsConstructor
public class GetAllCodigoImputacionesUseCaseImpl implements GetAllCodigoImputacionesUseCase {

    private final CodigoImputacionRepository codigoImputacionRepository;

    @Override
    public List<CodigoImputacion> getAllCodigoImputaciones() {
        return codigoImputacionRepository.findAll();
    }
}
