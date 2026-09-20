package um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.domain.model.LegajoCodigoImputacion;
import um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.domain.ports.in.CreateLegajoCodigoImputacionUseCase;
import um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.domain.ports.in.DeleteLegajoCodigoImputacionesByLegajoUseCase;
import um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.domain.ports.in.DeleteLegajoCodigoImputacionesByPeriodoUseCase;
import um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.domain.ports.in.FindLegajoCodigoImputacionesByLegajoAndCodigosUseCase;
import um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.domain.ports.in.FindLegajoCodigoImputacionesByLegajoUseCase;

@Service
@RequiredArgsConstructor
public class LegajoCodigoImputacionService {

    private final FindLegajoCodigoImputacionesByLegajoUseCase findLegajoCodigoImputacionesByLegajoUseCase;
    private final FindLegajoCodigoImputacionesByLegajoAndCodigosUseCase findLegajoCodigoImputacionesByLegajoAndCodigosUseCase;
    private final CreateLegajoCodigoImputacionUseCase createLegajoCodigoImputacionUseCase;
    private final DeleteLegajoCodigoImputacionesByLegajoUseCase deleteLegajoCodigoImputacionesByLegajoUseCase;
    private final DeleteLegajoCodigoImputacionesByPeriodoUseCase deleteLegajoCodigoImputacionesByPeriodoUseCase;

    public List<LegajoCodigoImputacion> findAllByLegajo(Long legajoId, Integer anho, Integer mes) {
        return findLegajoCodigoImputacionesByLegajoUseCase
                .findLegajoCodigoImputacionesByLegajo(legajoId, anho, mes);
    }

    public List<LegajoCodigoImputacion> findAllByLegajoAndCodigos(Long legajoId, Integer anho, Integer mes,
            List<Integer> codigoIds) {
        return findLegajoCodigoImputacionesByLegajoAndCodigosUseCase
                .findLegajoCodigoImputacionesByLegajoAndCodigos(legajoId, anho, mes, codigoIds);
    }

    public LegajoCodigoImputacion add(LegajoCodigoImputacion legajoCodigoImputacion) {
        return createLegajoCodigoImputacionUseCase.createLegajoCodigoImputacion(legajoCodigoImputacion);
    }

    public void deleteAllByLegajo(Long legajoId, Integer anho, Integer mes) {
        deleteLegajoCodigoImputacionesByLegajoUseCase
                .deleteLegajoCodigoImputacionesByLegajo(legajoId, anho, mes);
    }

    public void deleteAllByPeriodo(Integer anho, Integer mes) {
        deleteLegajoCodigoImputacionesByPeriodoUseCase
                .deleteLegajoCodigoImputacionesByPeriodo(anho, mes);
    }
}
