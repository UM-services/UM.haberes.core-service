package um.haberes.core.hexagonal.etec.presentismo_horas.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.presentismo_horas.domain.model.EtecPresentismoHoras;
import um.haberes.core.hexagonal.etec.presentismo_horas.domain.ports.out.EtecPresentismoHorasRepository;
import um.haberes.core.hexagonal.etec.presentismo_horas.infrastructure.persistence.entity.EtecPresentismoHorasEntity;
import um.haberes.core.hexagonal.etec.presentismo_horas.infrastructure.persistence.mapper.EtecPresentismoHorasMapper;
import um.haberes.core.hexagonal.etec.presentismo_horas.infrastructure.persistence.repository.JpaEtecPresentismoHorasRepository;

@Component
@RequiredArgsConstructor
public class JpaEtecPresentismoHorasRepositoryAdapter implements EtecPresentismoHorasRepository {

    private final JpaEtecPresentismoHorasRepository jpaRepository;
    private final EtecPresentismoHorasMapper mapper;
    private final EntityManager entityManager;

    @Override
    public EtecPresentismoHoras save(EtecPresentismoHoras presentismo) {
        EtecPresentismoHorasEntity saved = jpaRepository.save(mapper.toEntity(presentismo));
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            jpaRepository.flush();
            entityManager.clear();
        }
        return jpaRepository.findById(saved.getPresentismoHorasId())
                .map(mapper::toDomain)
                .orElseGet(() -> mapper.toDomain(saved));
    }

    @Override
    public Optional<EtecPresentismoHoras> findById(Long presentismoHorasId) {
        return jpaRepository.findById(presentismoHorasId).map(mapper::toDomain);
    }

    @Override
    public Optional<EtecPresentismoHoras> findByLegajoIdAndPeriodo(
            Long legajoId, Integer anho, Integer mes) {
        return jpaRepository.findByLegajoIdAndAnhoAndMes(legajoId, anho, mes).map(mapper::toDomain);
    }

    @Override
    public List<EtecPresentismoHoras> findAllByPeriodo(Integer anho, Integer mes) {
        return jpaRepository.findAllByAnhoAndMesOrderByLegajoIdAsc(anho, mes).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByLegajoIdAndPeriodo(Long legajoId, Integer anho, Integer mes) {
        return jpaRepository.existsByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
    }

    @Override
    public void deleteById(Long presentismoHorasId) {
        jpaRepository.deleteById(presentismoHorasId);
    }
}
