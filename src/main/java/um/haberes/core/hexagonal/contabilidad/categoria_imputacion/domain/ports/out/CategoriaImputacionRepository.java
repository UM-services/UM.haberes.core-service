package um.haberes.core.hexagonal.contabilidad.categoria_imputacion.domain.ports.out;

import java.util.List;
import java.util.Optional;

import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.domain.model.CategoriaImputacion;

public interface CategoriaImputacionRepository {

    CategoriaImputacion create(CategoriaImputacion categoriaImputacion);

    Optional<CategoriaImputacion> findById(Long categoriaImputacionId);

    Optional<CategoriaImputacion> findByUnique(Integer dependenciaId, Integer facultadId, Integer geograficaId,
            Integer categoriaId);

    List<CategoriaImputacion> findAll();

    Optional<CategoriaImputacion> update(Long categoriaImputacionId, CategoriaImputacion categoriaImputacion);
}
