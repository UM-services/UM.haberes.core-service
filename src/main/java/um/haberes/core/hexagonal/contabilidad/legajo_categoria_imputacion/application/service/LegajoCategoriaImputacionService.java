package um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.domain.model.LegajoCategoriaImputacion;
import um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.domain.ports.in.CreateLegajoCategoriaImputacionUseCase;
import um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.domain.ports.in.DeleteLegajoCategoriaImputacionesByLegajoUseCase;
import um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.domain.ports.in.DeleteLegajoCategoriaImputacionesByPeriodoUseCase;
import um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.domain.ports.in.FindLegajoCategoriaImputacionesByLegajoUseCase;

@Service
@RequiredArgsConstructor
public class LegajoCategoriaImputacionService {

    private final FindLegajoCategoriaImputacionesByLegajoUseCase findLegajoCategoriaImputacionesByLegajoUseCase;
    private final CreateLegajoCategoriaImputacionUseCase createLegajoCategoriaImputacionUseCase;
    private final DeleteLegajoCategoriaImputacionesByLegajoUseCase deleteLegajoCategoriaImputacionesByLegajoUseCase;
    private final DeleteLegajoCategoriaImputacionesByPeriodoUseCase deleteLegajoCategoriaImputacionesByPeriodoUseCase;

    public List<LegajoCategoriaImputacion> findAllByLegajo(Long legajoId, Integer anho, Integer mes) {
        return findLegajoCategoriaImputacionesByLegajoUseCase
                .findLegajoCategoriaImputacionesByLegajo(legajoId, anho, mes);
    }

    public LegajoCategoriaImputacion add(LegajoCategoriaImputacion legajoCategoriaImputacion) {
        return createLegajoCategoriaImputacionUseCase.createLegajoCategoriaImputacion(legajoCategoriaImputacion);
    }

    public void deleteAllByLegajo(Long legajoId, Integer anho, Integer mes) {
        deleteLegajoCategoriaImputacionesByLegajoUseCase
                .deleteLegajoCategoriaImputacionesByLegajo(legajoId, anho, mes);
    }

    public void deleteAllByPeriodo(Integer anho, Integer mes) {
        deleteLegajoCategoriaImputacionesByPeriodoUseCase
                .deleteLegajoCategoriaImputacionesByPeriodo(anho, mes);
    }
}
