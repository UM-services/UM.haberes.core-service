package um.haberes.core.hexagonal.contabilidad.categoria_imputacion.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.application.exception.CategoriaImputacionException;
import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.domain.model.CategoriaImputacion;
import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.domain.ports.in.CreateCategoriaImputacionUseCase;
import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.domain.ports.in.GetAllCategoriaImputacionesUseCase;
import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.domain.ports.in.GetCategoriaImputacionByIdUseCase;
import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.domain.ports.in.GetCategoriaImputacionByUniqueUseCase;
import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.domain.ports.in.UpdateCategoriaImputacionUseCase;

@Service
@RequiredArgsConstructor
public class CategoriaImputacionService {

    private final GetAllCategoriaImputacionesUseCase getAllCategoriaImputacionesUseCase;
    private final GetCategoriaImputacionByIdUseCase getCategoriaImputacionByIdUseCase;
    private final GetCategoriaImputacionByUniqueUseCase getCategoriaImputacionByUniqueUseCase;
    private final CreateCategoriaImputacionUseCase createCategoriaImputacionUseCase;
    private final UpdateCategoriaImputacionUseCase updateCategoriaImputacionUseCase;

    public List<CategoriaImputacion> getAllCategoriaImputaciones() {
        return getAllCategoriaImputacionesUseCase.getAllCategoriaImputaciones();
    }

    public CategoriaImputacion getCategoriaImputacionById(Long categoriaImputacionId) {
        return getCategoriaImputacionByIdUseCase.getCategoriaImputacionById(categoriaImputacionId)
                .orElseThrow(() -> new CategoriaImputacionException(categoriaImputacionId));
    }

    public CategoriaImputacion getCategoriaImputacionByUnique(Integer dependenciaId, Integer facultadId,
            Integer geograficaId, Integer categoriaId) {
        return getCategoriaImputacionByUniqueUseCase
                .getCategoriaImputacionByUnique(dependenciaId, facultadId, geograficaId, categoriaId)
                .orElseThrow(() -> new CategoriaImputacionException(dependenciaId, facultadId, geograficaId,
                        categoriaId));
    }

    public CategoriaImputacion createCategoriaImputacion(CategoriaImputacion categoriaImputacion) {
        return createCategoriaImputacionUseCase.createCategoriaImputacion(categoriaImputacion);
    }

    public CategoriaImputacion updateCategoriaImputacion(Long categoriaImputacionId,
            CategoriaImputacion categoriaImputacion) {
        return updateCategoriaImputacionUseCase
                .updateCategoriaImputacion(categoriaImputacionId, categoriaImputacion)
                .orElseThrow(() -> new CategoriaImputacionException(categoriaImputacionId));
    }
}
