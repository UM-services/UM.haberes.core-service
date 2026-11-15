package um.haberes.core.hexagonal.etec.arraigo_escala.infrastructure.persistence.adapter;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.arraigo_escala.domain.model.EscalaArraigoEtec;
import um.haberes.core.hexagonal.etec.arraigo_escala.domain.ports.out.EscalaArraigoEtecRepository;
import um.haberes.core.hexagonal.etec.arraigo_escala.infrastructure.persistence.mapper.EscalaArraigoEtecMapper;
import um.haberes.core.hexagonal.etec.arraigo_escala.infrastructure.persistence.repository.JpaEscalaArraigoEtecRepository;

@Component
@RequiredArgsConstructor
public class JpaEscalaArraigoEtecRepositoryAdapter implements EscalaArraigoEtecRepository {

    private final JpaEscalaArraigoEtecRepository repository;

    private final EscalaArraigoEtecMapper mapper;

    @Override
    public List<EscalaArraigoEtec> findAll() {
        return repository.findAllByOrderByAniosMinimosAsc().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public List<EscalaArraigoEtec> saveAll(List<EscalaArraigoEtec> tramos) {
        return repository.saveAll(tramos.stream().map(mapper::toEntity).toList()).stream()
                .map(mapper::toDomain)
                .toList();
    }
}
