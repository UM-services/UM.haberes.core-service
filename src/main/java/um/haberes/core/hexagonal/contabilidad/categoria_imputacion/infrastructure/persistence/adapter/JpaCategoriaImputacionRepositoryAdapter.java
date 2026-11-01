package um.haberes.core.hexagonal.contabilidad.categoria_imputacion.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.domain.model.CategoriaImputacion;
import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.domain.ports.out.CategoriaImputacionRepository;
import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.infrastructure.persistence.entity.CategoriaImputacionEntity;
import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.infrastructure.persistence.mapper.CategoriaImputacionMapper;
import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.infrastructure.persistence.repository.JpaCategoriaImputacionRepository;

@Component
@RequiredArgsConstructor
public class JpaCategoriaImputacionRepositoryAdapter implements CategoriaImputacionRepository {

    private final JpaCategoriaImputacionRepository jpaCategoriaImputacionRepository;
    private final CategoriaImputacionMapper categoriaImputacionMapper;

    @Override
    public CategoriaImputacion create(CategoriaImputacion categoriaImputacion) {
        CategoriaImputacionEntity entity = categoriaImputacionMapper.toEntity(categoriaImputacion);
        return categoriaImputacionMapper.toDomain(jpaCategoriaImputacionRepository.save(entity));
    }

    @Override
    public Optional<CategoriaImputacion> findById(Long categoriaImputacionId) {
        return jpaCategoriaImputacionRepository.findByCategoriaImputacionId(categoriaImputacionId)
                .map(categoriaImputacionMapper::toDomain);
    }

    @Override
    public Optional<CategoriaImputacion> findByUnique(Integer dependenciaId, Integer facultadId, Integer geograficaId,
            Integer categoriaId) {
        return jpaCategoriaImputacionRepository
                .findByDependenciaIdAndFacultadIdAndGeograficaIdAndCategoriaId(dependenciaId, facultadId, geograficaId,
                        categoriaId)
                .map(categoriaImputacionMapper::toDomain);
    }

    @Override
    public List<CategoriaImputacion> findAll() {
        return jpaCategoriaImputacionRepository.findAll().stream()
                .map(categoriaImputacionMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CategoriaImputacion> update(Long categoriaImputacionId, CategoriaImputacion categoriaImputacion) {
        if (jpaCategoriaImputacionRepository.existsById(categoriaImputacionId)) {
            CategoriaImputacionEntity entity = categoriaImputacionMapper.toEntity(categoriaImputacion);
            entity.setCategoriaImputacionId(categoriaImputacionId);
            CategoriaImputacionEntity updatedEntity = jpaCategoriaImputacionRepository.save(entity);
            return Optional.of(categoriaImputacionMapper.toDomain(updatedEntity));
        }
        return Optional.empty();
    }
}
