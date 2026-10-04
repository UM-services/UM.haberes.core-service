package um.haberes.core.hexagonal.contabilidad.codigo_imputacion.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.application.exception.CodigoImputacionException;
import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.domain.model.CodigoImputacion;
import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.domain.ports.in.CreateCodigoImputacionUseCase;
import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.domain.ports.in.GetAllCodigoImputacionesUseCase;
import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.domain.ports.in.GetCodigoImputacionByIdUseCase;
import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.domain.ports.in.GetCodigoImputacionByUniqueUseCase;
import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.domain.ports.in.UpdateCodigoImputacionUseCase;

@Service
@RequiredArgsConstructor
public class CodigoImputacionService {

    private final GetAllCodigoImputacionesUseCase getAllCodigoImputacionesUseCase;
    private final GetCodigoImputacionByIdUseCase getCodigoImputacionByIdUseCase;
    private final GetCodigoImputacionByUniqueUseCase getCodigoImputacionByUniqueUseCase;
    private final CreateCodigoImputacionUseCase createCodigoImputacionUseCase;
    private final UpdateCodigoImputacionUseCase updateCodigoImputacionUseCase;

    public List<CodigoImputacion> getAllCodigoImputaciones() {
        return getAllCodigoImputacionesUseCase.getAllCodigoImputaciones();
    }

    public CodigoImputacion getCodigoImputacionById(Long codigoImputacionId) {
        return getCodigoImputacionByIdUseCase.getCodigoImputacionById(codigoImputacionId)
                .orElseThrow(() -> new CodigoImputacionException(codigoImputacionId));
    }

    public CodigoImputacion getCodigoImputacionByUnique(Integer dependenciaId, Integer facultadId,
            Integer geograficaId, Integer codigoId) {
        return getCodigoImputacionByUniqueUseCase
                .getCodigoImputacionByUnique(dependenciaId, facultadId, geograficaId, codigoId)
                .orElseThrow(() -> new CodigoImputacionException(dependenciaId, facultadId, geograficaId, codigoId));
    }

    public CodigoImputacion createCodigoImputacion(CodigoImputacion codigoImputacion) {
        return createCodigoImputacionUseCase.createCodigoImputacion(codigoImputacion);
    }

    public CodigoImputacion updateCodigoImputacion(Long codigoImputacionId, CodigoImputacion codigoImputacion) {
        return updateCodigoImputacionUseCase.updateCodigoImputacion(codigoImputacionId, codigoImputacion)
                .orElseThrow(() -> new CodigoImputacionException(codigoImputacionId));
    }
}
