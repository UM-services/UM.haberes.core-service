package um.haberes.core.hexagonal.contabilidad.categoria_imputacion.infrastructure.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.infrastructure.persistence.entity.CategoriaImputacionEntity;

@Repository
public interface JpaCategoriaImputacionRepository extends JpaRepository<CategoriaImputacionEntity, Long> {

    Optional<CategoriaImputacionEntity> findByCategoriaImputacionId(Long categoriaImputacionId);

    Optional<CategoriaImputacionEntity> findByDependenciaIdAndFacultadIdAndGeograficaIdAndCategoriaId(
            Integer dependenciaId, Integer facultadId, Integer geograficaId, Integer categoriaId);
}
