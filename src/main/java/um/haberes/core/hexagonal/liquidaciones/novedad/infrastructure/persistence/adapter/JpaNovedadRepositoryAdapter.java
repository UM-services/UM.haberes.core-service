package um.haberes.core.hexagonal.liquidaciones.novedad.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.out.NovedadRepository;
import um.haberes.core.hexagonal.liquidaciones.novedad.infrastructure.persistence.entity.NovedadEntity;
import um.haberes.core.hexagonal.liquidaciones.novedad.infrastructure.persistence.mapper.NovedadMapper;
import um.haberes.core.hexagonal.liquidaciones.novedad.infrastructure.persistence.repository.JpaNovedadRepository;

@Component
@RequiredArgsConstructor
public class JpaNovedadRepositoryAdapter implements NovedadRepository {

    private final JpaNovedadRepository jpaNovedadRepository;
    private final NovedadMapper novedadMapper;

    @Override
    public Novedad save(Novedad novedad) {
        return novedadMapper.toDomain(jpaNovedadRepository.save(novedadMapper.toEntity(novedad)));
    }

    @Override
    public List<Novedad> saveAll(List<Novedad> novedades) {
        List<NovedadEntity> entities = novedades.stream()
                .map(novedadMapper::toEntity)
                .collect(Collectors.toList());
        return toDomainList(jpaNovedadRepository.saveAll(entities));
    }

    @Override
    public Optional<Novedad> findByNovedadId(Long novedadId) {
        return jpaNovedadRepository.findByNovedadId(novedadId).map(novedadMapper::toDomain);
    }

    @Override
    public List<Novedad> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes) {
        return toDomainList(jpaNovedadRepository.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes));
    }

    @Override
    public List<Novedad> findAllByCodigoIdAndAnhoAndMes(Integer codigoId, Integer anho, Integer mes) {
        return toDomainList(jpaNovedadRepository.findAllByCodigoIdAndAnhoAndMes(codigoId, anho, mes,
                Sort.by("legajoId").ascending()));
    }

    @Override
    public List<Novedad> findAllByImportadoAndAnhoAndMes(Byte importado, Integer anho, Integer mes) {
        return toDomainList(jpaNovedadRepository.findAllByImportadoAndAnhoAndMes(importado, anho, mes));
    }

    @Override
    public List<Novedad> findAllByLegajoIdAndAnhoAndMesAndCodigoId(Long legajoId, Integer anho, Integer mes,
            Integer codigoId) {
        return toDomainList(
                jpaNovedadRepository.findAllByLegajoIdAndAnhoAndMesAndCodigoId(legajoId, anho, mes, codigoId));
    }

    @Override
    public Optional<Novedad> findByLegajoIdAndAnhoAndMesAndCodigoIdAndDependenciaId(Long legajoId, Integer anho,
            Integer mes, Integer codigoId, Integer dependenciaId) {
        return jpaNovedadRepository
                .findByLegajoIdAndAnhoAndMesAndCodigoIdAndDependenciaId(legajoId, anho, mes, codigoId, dependenciaId)
                .map(novedadMapper::toDomain);
    }

    @Override
    public Optional<Novedad> findByLegajoIdAndAnhoAndMesAndCodigoIdAndDependenciaIdIsNull(Long legajoId, Integer anho,
            Integer mes, Integer codigoId) {
        return jpaNovedadRepository
                .findByLegajoIdAndAnhoAndMesAndCodigoIdAndDependenciaIdIsNull(legajoId, anho, mes, codigoId)
                .map(novedadMapper::toDomain);
    }

    @Override
    public void deleteAllByAnhoAndMes(Integer anho, Integer mes) {
        jpaNovedadRepository.deleteAllByAnhoAndMes(anho, mes);
    }

    @Override
    public void deleteByNovedadId(Long novedadId) {
        jpaNovedadRepository.deleteById(novedadId);
    }

    private List<Novedad> toDomainList(List<NovedadEntity> entities) {
        return entities.stream()
                .map(novedadMapper::toDomain)
                .collect(Collectors.toList());
    }
}
