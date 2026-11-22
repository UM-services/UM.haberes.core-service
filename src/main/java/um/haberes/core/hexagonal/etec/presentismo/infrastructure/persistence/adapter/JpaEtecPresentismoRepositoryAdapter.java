package um.haberes.core.hexagonal.etec.presentismo.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.presentismo.domain.model.EtecPresentismo;
import um.haberes.core.hexagonal.etec.presentismo.domain.ports.out.EtecPresentismoRepository;
import um.haberes.core.hexagonal.etec.presentismo.infrastructure.persistence.entity.EtecPresentismoEntity;
import um.haberes.core.hexagonal.etec.presentismo.infrastructure.persistence.mapper.EtecPresentismoMapper;
import um.haberes.core.hexagonal.etec.presentismo.infrastructure.persistence.repository.JpaEtecPresentismoRepository;

@Component
@RequiredArgsConstructor
public class JpaEtecPresentismoRepositoryAdapter implements EtecPresentismoRepository {

    private final JpaEtecPresentismoRepository jpaRepository;
    private final EtecPresentismoMapper mapper;
    private final EntityManager entityManager;

    @Override
    public EtecPresentismo save(EtecPresentismo presentismo) {
        EtecPresentismoEntity saved = jpaRepository.save(mapper.toEntity(presentismo));
        // En una transacción abierta el contexto devuelve la misma instancia sin las
        // relaciones; se limpia para releer los nombres de cargo/dependencia/persona.
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            jpaRepository.flush();
            entityManager.clear();
        }
        return jpaRepository.findById(saved.getPresentismoId())
                .map(mapper::toDomain)
                .orElseGet(() -> mapper.toDomain(saved));
    }

    @Override
    public Optional<EtecPresentismo> findById(Long presentismoId) {
        return jpaRepository.findById(presentismoId).map(mapper::toDomain);
    }

    @Override
    public Optional<EtecPresentismo> findByAsignacionCargoIdAndPeriodo(
            Long asignacionCargoId, Integer anho, Integer mes) {
        return jpaRepository.findByAsignacionCargoIdAndAnhoAndMes(asignacionCargoId, anho, mes)
                .map(mapper::toDomain);
    }

    @Override
    public List<EtecPresentismo> findAllByLegajoIdAndPeriodo(Long legajoId, Integer anho, Integer mes) {
        return toDomainList(jpaRepository
                .findAllByAsignacionLegajoIdAndAnhoAndMesOrderByPresentismoIdAsc(legajoId, anho, mes));
    }

    @Override
    public List<EtecPresentismo> findAllByPeriodo(Integer anho, Integer mes) {
        return toDomainList(jpaRepository.findAllByAnhoAndMesOrderByPresentismoIdAsc(anho, mes));
    }

    @Override
    public boolean existsByAsignacionCargoIdAndPeriodo(Long asignacionCargoId, Integer anho, Integer mes) {
        return jpaRepository.existsByAsignacionCargoIdAndAnhoAndMes(asignacionCargoId, anho, mes);
    }

    @Override
    public void deleteById(Long presentismoId) {
        jpaRepository.deleteById(presentismoId);
    }

    @Override
    public void deleteAllByAsignacionCargoId(Long asignacionCargoId) {
        jpaRepository.deleteAllByAsignacionCargoId(asignacionCargoId);
    }

    private List<EtecPresentismo> toDomainList(List<EtecPresentismoEntity> entities) {
        return entities.stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
