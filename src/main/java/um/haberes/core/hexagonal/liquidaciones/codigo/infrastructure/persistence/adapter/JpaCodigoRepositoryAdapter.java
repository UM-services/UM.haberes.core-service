package um.haberes.core.hexagonal.liquidaciones.codigo.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.model.Codigo;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.ports.out.CodigoRepository;
import um.haberes.core.hexagonal.liquidaciones.codigo.infrastructure.persistence.entity.CodigoEntity;
import um.haberes.core.hexagonal.liquidaciones.codigo.infrastructure.persistence.mapper.CodigoMapper;
import um.haberes.core.hexagonal.liquidaciones.codigo.infrastructure.persistence.repository.JpaCodigoRepository;

@Component
@RequiredArgsConstructor
public class JpaCodigoRepositoryAdapter implements CodigoRepository {

    private final JpaCodigoRepository jpaCodigoRepository;
    private final CodigoMapper codigoMapper;

    @Override
    public List<Codigo> findAll() {
        return toDomainList(jpaCodigoRepository.findAll(Sort.by("codigoId").ascending()));
    }

    @Override
    public List<Codigo> findAllByCodigoIdIn(List<Integer> codigoIds) {
        return toDomainList(jpaCodigoRepository.findAllByCodigoIdIn(codigoIds, Sort.by("codigoId").ascending()));
    }

    @Override
    public List<Codigo> findAllByTransferible(Byte transferible) {
        return toDomainList(jpaCodigoRepository.findAllByTransferible(transferible));
    }

    @Override
    public Optional<Codigo> findByCodigoId(Integer codigoId) {
        return jpaCodigoRepository.findById(codigoId).map(codigoMapper::toDomain);
    }

    @Override
    public Optional<Codigo> findLast() {
        return jpaCodigoRepository.findTopByOrderByCodigoId().map(codigoMapper::toDomain);
    }

    @Override
    public Codigo save(Codigo codigo) {
        return codigoMapper.toDomain(jpaCodigoRepository.save(codigoMapper.toEntity(codigo)));
    }

    @Override
    public List<Codigo> saveAll(List<Codigo> codigos) {
        List<CodigoEntity> entities = codigos.stream()
                .map(codigoMapper::toEntity)
                .collect(Collectors.toList());
        return toDomainList(jpaCodigoRepository.saveAll(entities));
    }

    @Override
    public void deleteByCodigoId(Integer codigoId) {
        jpaCodigoRepository.deleteById(codigoId);
    }

    private List<Codigo> toDomainList(List<CodigoEntity> entities) {
        return entities.stream()
                .map(codigoMapper::toDomain)
                .collect(Collectors.toList());
    }
}
