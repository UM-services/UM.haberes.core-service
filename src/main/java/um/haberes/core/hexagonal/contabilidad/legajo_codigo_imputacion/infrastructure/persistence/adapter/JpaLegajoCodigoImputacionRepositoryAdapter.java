package um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.infrastructure.persistence.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.domain.model.LegajoCodigoImputacion;
import um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.domain.ports.out.LegajoCodigoImputacionRepository;
import um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.infrastructure.persistence.entity.LegajoCodigoImputacionEntity;
import um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.infrastructure.persistence.mapper.LegajoCodigoImputacionMapper;
import um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.infrastructure.persistence.repository.JpaLegajoCodigoImputacionRepository;

@Component
@RequiredArgsConstructor
public class JpaLegajoCodigoImputacionRepositoryAdapter implements LegajoCodigoImputacionRepository {

    private final JpaLegajoCodigoImputacionRepository jpaLegajoCodigoImputacionRepository;
    private final LegajoCodigoImputacionMapper legajoCodigoImputacionMapper;

    @Override
    public LegajoCodigoImputacion create(LegajoCodigoImputacion legajoCodigoImputacion) {
        LegajoCodigoImputacionEntity entity = legajoCodigoImputacionMapper.toEntity(legajoCodigoImputacion);
        return legajoCodigoImputacionMapper.toDomain(jpaLegajoCodigoImputacionRepository.save(entity));
    }

    @Override
    public List<LegajoCodigoImputacion> findByLegajo(Long legajoId, Integer anho, Integer mes) {
        return jpaLegajoCodigoImputacionRepository.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes).stream()
                .map(legajoCodigoImputacionMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<LegajoCodigoImputacion> findByLegajoAndCodigos(Long legajoId, Integer anho, Integer mes,
            List<Integer> codigoIds) {
        return jpaLegajoCodigoImputacionRepository
                .findAllByLegajoIdAndAnhoAndMesAndCodigoIdIn(legajoId, anho, mes, codigoIds).stream()
                .map(legajoCodigoImputacionMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAllByLegajo(Long legajoId, Integer anho, Integer mes) {
        jpaLegajoCodigoImputacionRepository.deleteAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
    }

    @Override
    public void deleteAllByPeriodo(Integer anho, Integer mes) {
        jpaLegajoCodigoImputacionRepository.deleteAllByAnhoAndMes(anho, mes);
    }
}
