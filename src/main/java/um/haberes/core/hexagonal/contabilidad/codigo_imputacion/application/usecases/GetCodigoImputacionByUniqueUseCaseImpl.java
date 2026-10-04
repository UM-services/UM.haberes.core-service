package um.haberes.core.hexagonal.contabilidad.codigo_imputacion.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.domain.model.CodigoImputacion;
import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.domain.ports.in.GetCodigoImputacionByUniqueUseCase;
import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.domain.ports.out.CodigoImputacionRepository;

@Component
@RequiredArgsConstructor
public class GetCodigoImputacionByUniqueUseCaseImpl implements GetCodigoImputacionByUniqueUseCase {

    private final CodigoImputacionRepository codigoImputacionRepository;

    @Override
    public Optional<CodigoImputacion> getCodigoImputacionByUnique(Integer dependenciaId, Integer facultadId,
            Integer geograficaId, Integer codigoId) {
        return codigoImputacionRepository.findByUnique(dependenciaId, facultadId, geograficaId, codigoId);
    }
}
