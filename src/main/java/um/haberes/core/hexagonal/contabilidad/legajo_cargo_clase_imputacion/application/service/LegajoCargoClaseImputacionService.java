package um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.domain.model.LegajoCargoClaseImputacion;
import um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.domain.ports.in.CreateLegajoCargoClaseImputacionUseCase;
import um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.domain.ports.in.DeleteLegajoCargoClaseImputacionesByLegajoUseCase;
import um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.domain.ports.in.DeleteLegajoCargoClaseImputacionesByPeriodoUseCase;
import um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.domain.ports.in.FindLegajoCargoClaseImputacionesByLegajoUseCase;

@Service
@RequiredArgsConstructor
public class LegajoCargoClaseImputacionService {

    private final FindLegajoCargoClaseImputacionesByLegajoUseCase findLegajoCargoClaseImputacionesByLegajoUseCase;
    private final CreateLegajoCargoClaseImputacionUseCase createLegajoCargoClaseImputacionUseCase;
    private final DeleteLegajoCargoClaseImputacionesByLegajoUseCase deleteLegajoCargoClaseImputacionesByLegajoUseCase;
    private final DeleteLegajoCargoClaseImputacionesByPeriodoUseCase deleteLegajoCargoClaseImputacionesByPeriodoUseCase;

    public List<LegajoCargoClaseImputacion> findAllByLegajo(Long legajoId, Integer anho, Integer mes) {
        return findLegajoCargoClaseImputacionesByLegajoUseCase
                .findLegajoCargoClaseImputacionesByLegajo(legajoId, anho, mes);
    }

    public LegajoCargoClaseImputacion add(LegajoCargoClaseImputacion legajoCargoClaseImputacion) {
        return createLegajoCargoClaseImputacionUseCase.createLegajoCargoClaseImputacion(legajoCargoClaseImputacion);
    }

    public void deleteAllByLegajo(Long legajoId, Integer anho, Integer mes) {
        deleteLegajoCargoClaseImputacionesByLegajoUseCase
                .deleteLegajoCargoClaseImputacionesByLegajo(legajoId, anho, mes);
    }

    public void deleteAllByPeriodo(Integer anho, Integer mes) {
        deleteLegajoCargoClaseImputacionesByPeriodoUseCase
                .deleteLegajoCargoClaseImputacionesByPeriodo(anho, mes);
    }
}
