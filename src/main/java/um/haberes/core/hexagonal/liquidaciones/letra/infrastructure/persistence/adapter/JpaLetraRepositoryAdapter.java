package um.haberes.core.hexagonal.liquidaciones.letra.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.letra.domain.model.Letra;
import um.haberes.core.hexagonal.liquidaciones.letra.domain.ports.out.LetraRepository;
import um.haberes.core.hexagonal.liquidaciones.letra.infrastructure.persistence.entity.LetraEntity;
import um.haberes.core.hexagonal.liquidaciones.letra.infrastructure.persistence.mapper.LetraMapper;
import um.haberes.core.hexagonal.liquidaciones.letra.infrastructure.persistence.repository.JpaLetraRepository;

@Component
@RequiredArgsConstructor
public class JpaLetraRepositoryAdapter implements LetraRepository {

    private final JpaLetraRepository jpaLetraRepository;
    private final LetraMapper letraMapper;

    @Override
    public Letra save(Letra letra) {
        return letraMapper.toDomain(jpaLetraRepository.save(letraMapper.toEntity(letra)));
    }

    @Override
    public List<Letra> saveAll(List<Letra> letras) {
        List<LetraEntity> entities = letras.stream()
                .map(letraMapper::toEntity)
                .collect(Collectors.toList());
        return toDomainList(jpaLetraRepository.saveAll(entities));
    }

    @Override
    public Optional<Letra> findByLetraId(Long letraId) {
        return jpaLetraRepository.findByLetraId(letraId).map(letraMapper::toDomain);
    }

    @Override
    public Optional<Letra> findByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes) {
        return jpaLetraRepository.findByLegajoIdAndAnhoAndMes(legajoId, anho, mes).map(letraMapper::toDomain);
    }

    @Override
    public List<Letra> findAllByAnhoAndMes(Integer anho, Integer mes, Integer limit) {
        return toDomainList(jpaLetraRepository.findAllByAnhoAndMes(anho, mes, PageRequest.of(0, limit)));
    }

    @Override
    public void deleteAllByAnhoAndMes(Integer anho, Integer mes) {
        jpaLetraRepository.deleteAllByAnhoAndMes(anho, mes);
    }

    @Override
    public void deleteAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes) {
        jpaLetraRepository.deleteByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
    }

    private List<Letra> toDomainList(List<LetraEntity> entities) {
        return entities.stream()
                .map(letraMapper::toDomain)
                .collect(Collectors.toList());
    }
}
