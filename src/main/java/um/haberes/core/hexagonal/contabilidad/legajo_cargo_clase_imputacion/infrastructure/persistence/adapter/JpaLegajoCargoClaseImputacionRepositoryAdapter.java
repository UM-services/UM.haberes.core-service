package um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.infrastructure.persistence.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.domain.model.LegajoCargoClaseImputacion;
import um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.domain.ports.out.LegajoCargoClaseImputacionRepository;
import um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.infrastructure.persistence.entity.LegajoCargoClaseImputacionEntity;
import um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.infrastructure.persistence.mapper.LegajoCargoClaseImputacionMapper;
import um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.infrastructure.persistence.repository.JpaLegajoCargoClaseImputacionRepository;

@Component
@RequiredArgsConstructor
public class JpaLegajoCargoClaseImputacionRepositoryAdapter implements LegajoCargoClaseImputacionRepository {

    private final JpaLegajoCargoClaseImputacionRepository jpaLegajoCargoClaseImputacionRepository;
    private final LegajoCargoClaseImputacionMapper legajoCargoClaseImputacionMapper;

    @Override
    public LegajoCargoClaseImputacion create(LegajoCargoClaseImputacion legajoCargoClaseImputacion) {
        LegajoCargoClaseImputacionEntity entity = legajoCargoClaseImputacionMapper.toEntity(legajoCargoClaseImputacion);
        return legajoCargoClaseImputacionMapper.toDomain(jpaLegajoCargoClaseImputacionRepository.save(entity));
    }

    @Override
    public List<LegajoCargoClaseImputacion> findByLegajo(Long legajoId, Integer anho, Integer mes) {
        return jpaLegajoCargoClaseImputacionRepository.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes).stream()
                .map(legajoCargoClaseImputacionMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAllByLegajo(Long legajoId, Integer anho, Integer mes) {
        jpaLegajoCargoClaseImputacionRepository.deleteAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
    }

    @Override
    public void deleteAllByPeriodo(Integer anho, Integer mes) {
        jpaLegajoCargoClaseImputacionRepository.deleteAllByAnhoAndMes(anho, mes);
    }
}
