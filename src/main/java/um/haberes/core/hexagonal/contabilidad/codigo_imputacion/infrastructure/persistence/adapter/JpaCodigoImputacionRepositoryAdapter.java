package um.haberes.core.hexagonal.contabilidad.codigo_imputacion.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.domain.model.CodigoImputacion;
import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.domain.ports.out.CodigoImputacionRepository;
import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.infrastructure.persistence.entity.CodigoImputacionEntity;
import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.infrastructure.persistence.mapper.CodigoImputacionMapper;
import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.infrastructure.persistence.repository.JpaCodigoImputacionRepository;

@Component
@RequiredArgsConstructor
public class JpaCodigoImputacionRepositoryAdapter implements CodigoImputacionRepository {

    private final JpaCodigoImputacionRepository jpaCodigoImputacionRepository;
    private final CodigoImputacionMapper codigoImputacionMapper;

    @Override
    public CodigoImputacion create(CodigoImputacion codigoImputacion) {
        CodigoImputacionEntity entity = codigoImputacionMapper.toEntity(codigoImputacion);
        return codigoImputacionMapper.toDomain(jpaCodigoImputacionRepository.save(entity));
    }

    @Override
    public Optional<CodigoImputacion> findById(Long codigoImputacionId) {
        return jpaCodigoImputacionRepository.findByCodigoImputacionId(codigoImputacionId)
                .map(codigoImputacionMapper::toDomain);
    }

    @Override
    public Optional<CodigoImputacion> findByUnique(Integer dependenciaId, Integer facultadId, Integer geograficaId,
            Integer codigoId) {
        return jpaCodigoImputacionRepository
                .findByDependenciaIdAndFacultadIdAndGeograficaIdAndCodigoId(dependenciaId, facultadId, geograficaId,
                        codigoId)
                .map(codigoImputacionMapper::toDomain);
    }

    @Override
    public List<CodigoImputacion> findAll() {
        return jpaCodigoImputacionRepository.findAll().stream()
                .map(codigoImputacionMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CodigoImputacion> update(Long codigoImputacionId, CodigoImputacion codigoImputacion) {
        if (jpaCodigoImputacionRepository.existsById(codigoImputacionId)) {
            CodigoImputacionEntity entity = codigoImputacionMapper.toEntity(codigoImputacion);
            entity.setCodigoImputacionId(codigoImputacionId);
            CodigoImputacionEntity updatedEntity = jpaCodigoImputacionRepository.save(entity);
            return Optional.of(codigoImputacionMapper.toDomain(updatedEntity));
        }
        return Optional.empty();
    }
}
