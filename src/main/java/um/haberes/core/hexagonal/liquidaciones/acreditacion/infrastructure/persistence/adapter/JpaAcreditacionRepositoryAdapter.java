package um.haberes.core.hexagonal.liquidaciones.acreditacion.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.model.Acreditacion;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.ports.out.AcreditacionRepository;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.infrastructure.persistence.entity.AcreditacionEntity;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.infrastructure.persistence.mapper.AcreditacionMapper;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.infrastructure.persistence.repository.JpaAcreditacionRepository;

@Component
@RequiredArgsConstructor
public class JpaAcreditacionRepositoryAdapter implements AcreditacionRepository {

    private final JpaAcreditacionRepository jpaAcreditacionRepository;
    private final AcreditacionMapper acreditacionMapper;

    @Override
    public List<Acreditacion> findAll() {
        return jpaAcreditacionRepository.findAll().stream()
                .map(acreditacionMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Acreditacion> findByAcreditacionId(Long acreditacionId) {
        return jpaAcreditacionRepository.findByAcreditacionId(acreditacionId)
                .map(acreditacionMapper::toDomain);
    }

    @Override
    public Optional<Acreditacion> findByAnhoAndMes(Integer anho, Integer mes) {
        return jpaAcreditacionRepository.findByAnhoAndMes(anho, mes)
                .map(acreditacionMapper::toDomain);
    }

    @Override
    public Acreditacion create(Acreditacion acreditacion) {
        AcreditacionEntity entity = acreditacionMapper.toEntity(acreditacion);
        return acreditacionMapper.toDomain(jpaAcreditacionRepository.save(entity));
    }

    @Override
    public Optional<Acreditacion> update(Long acreditacionId, Acreditacion acreditacion) {
        AcreditacionEntity mapped = acreditacionMapper.toEntity(acreditacion);
        return jpaAcreditacionRepository.findByAcreditacionId(acreditacionId)
                .map(entity -> {
                    entity.setAnho(mapped.getAnho());
                    entity.setMes(mapped.getMes());
                    entity.setAcreditado(mapped.getAcreditado());
                    entity.setLimiteNovedades(mapped.getLimiteNovedades());
                    entity.setFechaContable(mapped.getFechaContable());
                    entity.setOrdenContable(mapped.getOrdenContable());
                    entity.setSueldosOriginal(mapped.getSueldosOriginal());
                    entity.setSueldosAjustados(mapped.getSueldosAjustados());
                    entity.setContribucionesPatronales(mapped.getContribucionesPatronales());
                    return acreditacionMapper.toDomain(jpaAcreditacionRepository.save(entity));
                });
    }

    @Override
    public void deleteById(Long acreditacionId) {
        jpaAcreditacionRepository.deleteById(acreditacionId);
    }
}
