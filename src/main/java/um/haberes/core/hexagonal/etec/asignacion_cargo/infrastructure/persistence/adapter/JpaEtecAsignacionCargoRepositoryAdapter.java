package um.haberes.core.hexagonal.etec.asignacion_cargo.infrastructure.persistence.adapter;

import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.asignacion_cargo.domain.model.EtecAsignacionCargo;
import um.haberes.core.hexagonal.etec.asignacion_cargo.domain.ports.out.EtecAsignacionCargoRepository;
import um.haberes.core.hexagonal.etec.asignacion_cargo.infrastructure.persistence.entity.EtecAsignacionCargoEntity;
import um.haberes.core.hexagonal.etec.asignacion_cargo.infrastructure.persistence.mapper.EtecAsignacionCargoMapper;
import um.haberes.core.hexagonal.etec.asignacion_cargo.infrastructure.persistence.repository.JpaEtecAsignacionCargoRepository;
import um.haberes.core.util.Periodo;

@Component
@RequiredArgsConstructor
public class JpaEtecAsignacionCargoRepositoryAdapter implements EtecAsignacionCargoRepository {

    private final JpaEtecAsignacionCargoRepository jpaRepository;
    private final EtecAsignacionCargoMapper mapper;
    private final EntityManager entityManager;

    @Override
    public EtecAsignacionCargo save(EtecAsignacionCargo asignacion) {
        EtecAsignacionCargoEntity saved = jpaRepository.save(mapper.toEntity(asignacion));
        // En una transacción abierta (update) el contexto de persistencia devuelve la misma
        // instancia sin las relaciones; se limpia para releerlas y devolver los nombres.
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            jpaRepository.flush();
            entityManager.clear();
        }
        return jpaRepository.findById(saved.getAsignacionCargoId())
                .map(mapper::toDomain)
                .orElseGet(() -> mapper.toDomain(saved));
    }

    @Override
    public Optional<EtecAsignacionCargo> findById(Long asignacionCargoId) {
        return jpaRepository.findById(asignacionCargoId).map(mapper::toDomain);
    }

    @Override
    public List<EtecAsignacionCargo> findAllByLegajoId(Long legajoId) {
        return toDomainList(jpaRepository.findAllByLegajoIdOrderByFechaAltaDesc(legajoId));
    }

    @Override
    public List<EtecAsignacionCargo> findAllVigentesByPeriodo(Integer anho, Integer mes) {
        OffsetDateTime firstDay = Periodo.firstDay(anho, mes);
        OffsetDateTime lastDay = Periodo.lastDay(anho, mes);
        return toDomainList(Stream.concat(
                jpaRepository.findAllByFechaAltaLessThanEqualAndFechaBajaIsNull(firstDay).stream(),
                jpaRepository.findAllByFechaAltaLessThanEqualAndFechaBajaGreaterThanEqual(firstDay, lastDay).stream())
                .collect(Collectors.toList()));
    }

    @Override
    public List<EtecAsignacionCargo> findAllVigentesByLegajoAndPeriodo(Long legajoId, Integer anho, Integer mes) {
        OffsetDateTime firstDay = Periodo.firstDay(anho, mes);
        OffsetDateTime lastDay = Periodo.lastDay(anho, mes);
        return toDomainList(Stream.concat(
                jpaRepository.findAllByLegajoIdAndFechaAltaLessThanEqualAndFechaBajaIsNull(legajoId, firstDay).stream(),
                jpaRepository.findAllByLegajoIdAndFechaAltaLessThanEqualAndFechaBajaGreaterThanEqual(legajoId,
                        firstDay, lastDay).stream())
                .collect(Collectors.toList()));
    }

    @Override
    public List<Integer> findDistinctCategoriaIds() {
        // El volumen de asignaciones ETEC es bajo; se resuelve en memoria para no usar consultas anotadas.
        return jpaRepository.findAll().stream()
                .map(EtecAsignacionCargoEntity::getCategoriaId)
                .filter(Objects::nonNull)
                .distinct()
                .sorted(Comparator.naturalOrder())
                .toList();
    }

    @Override
    public boolean existsByCategoriaId(Integer categoriaId) {
        return jpaRepository.existsByCategoriaId(categoriaId);
    }

    @Override
    public void deleteById(Long asignacionCargoId) {
        jpaRepository.deleteById(asignacionCargoId);
    }

    private List<EtecAsignacionCargo> toDomainList(List<EtecAsignacionCargoEntity> entities) {
        return entities.stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
