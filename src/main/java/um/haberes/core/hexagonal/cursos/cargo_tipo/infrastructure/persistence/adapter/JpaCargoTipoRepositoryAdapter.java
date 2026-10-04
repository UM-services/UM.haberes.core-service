package um.haberes.core.hexagonal.cursos.cargo_tipo.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.cargo_tipo.domain.model.CargoTipo;
import um.haberes.core.hexagonal.cursos.cargo_tipo.domain.ports.out.CargoTipoRepository;
import um.haberes.core.hexagonal.cursos.cargo_tipo.infrastructure.persistence.entity.CargoTipoEntity;
import um.haberes.core.hexagonal.cursos.cargo_tipo.infrastructure.persistence.mapper.CargoTipoMapper;
import um.haberes.core.hexagonal.cursos.cargo_tipo.infrastructure.persistence.repository.JpaCargoTipoRepository;

@Component
@RequiredArgsConstructor
public class JpaCargoTipoRepositoryAdapter implements CargoTipoRepository {

    private final JpaCargoTipoRepository jpaCargoTipoRepository;
    private final CargoTipoMapper cargoTipoMapper;

    @Override
    public List<CargoTipo> findAll() {
        return toDomainList(jpaCargoTipoRepository.findAll());
    }

    @Override
    public List<CargoTipo> findAllByCargoTipoIdIn(List<Integer> cargoTipoIds) {
        return toDomainList(jpaCargoTipoRepository.findAllByCargoTipoIdIn(cargoTipoIds));
    }

    @Override
    public Optional<CargoTipo> findByCargoTipoId(Integer cargoTipoId) {
        return jpaCargoTipoRepository.findByCargoTipoId(cargoTipoId).map(cargoTipoMapper::toDomain);
    }

    private List<CargoTipo> toDomainList(List<CargoTipoEntity> entities) {
        return entities.stream()
                .map(cargoTipoMapper::toDomain)
                .collect(Collectors.toList());
    }
}
