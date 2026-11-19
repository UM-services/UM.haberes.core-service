package um.haberes.core.hexagonal.etec.control_etec.infrastructure.persistence.adapter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.control_etec.domain.model.ControlEtec;
import um.haberes.core.hexagonal.etec.control_etec.domain.ports.out.ControlEtecRepository;
import um.haberes.core.hexagonal.etec.control_etec.infrastructure.persistence.mapper.ControlEtecMapper;
import um.haberes.core.hexagonal.etec.control_etec.infrastructure.persistence.repository.JpaControlEtecRepository;

@Component
@RequiredArgsConstructor
public class JpaControlEtecRepositoryAdapter implements ControlEtecRepository {

    private final JpaControlEtecRepository repository;

    private final ControlEtecMapper mapper;

    @Override
    public Optional<ControlEtec> findByPeriodo(Integer anho, Integer mes) {
        return repository.findByAnhoAndMes(anho, mes).map(mapper::toDomain);
    }

    @Override
    public boolean existsByPeriodo(Integer anho, Integer mes) {
        return repository.existsByAnhoAndMes(anho, mes);
    }

    @Override
    public ControlEtec save(ControlEtec controlEtec) {
        return mapper.toDomain(repository.save(mapper.toEntity(controlEtec)));
    }
}
