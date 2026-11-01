package um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.infrastructure.persistence.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.domain.model.LegajoCategoriaImputacion;
import um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.domain.ports.out.LegajoCategoriaImputacionRepository;
import um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.infrastructure.persistence.entity.LegajoCategoriaImputacionEntity;
import um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.infrastructure.persistence.mapper.LegajoCategoriaImputacionMapper;
import um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.infrastructure.persistence.repository.JpaLegajoCategoriaImputacionRepository;

@Component
@RequiredArgsConstructor
public class JpaLegajoCategoriaImputacionRepositoryAdapter implements LegajoCategoriaImputacionRepository {

    private final JpaLegajoCategoriaImputacionRepository jpaLegajoCategoriaImputacionRepository;
    private final LegajoCategoriaImputacionMapper legajoCategoriaImputacionMapper;

    @Override
    public LegajoCategoriaImputacion create(LegajoCategoriaImputacion legajoCategoriaImputacion) {
        LegajoCategoriaImputacionEntity entity = legajoCategoriaImputacionMapper.toEntity(legajoCategoriaImputacion);
        return legajoCategoriaImputacionMapper.toDomain(jpaLegajoCategoriaImputacionRepository.save(entity));
    }

    @Override
    public List<LegajoCategoriaImputacion> findByLegajo(Long legajoId, Integer anho, Integer mes) {
        return jpaLegajoCategoriaImputacionRepository.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes).stream()
                .map(legajoCategoriaImputacionMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAllByLegajo(Long legajoId, Integer anho, Integer mes) {
        jpaLegajoCategoriaImputacionRepository.deleteAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
    }

    @Override
    public void deleteAllByPeriodo(Integer anho, Integer mes) {
        jpaLegajoCategoriaImputacionRepository.deleteAllByAnhoAndMes(anho, mes);
    }
}
