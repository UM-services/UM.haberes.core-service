package um.haberes.core.hexagonal.etec.presentismo.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import um.haberes.core.hexagonal.etec.presentismo.infrastructure.persistence.entity.EtecPresentismoEntity;

@Repository
public interface JpaEtecPresentismoRepository extends JpaRepository<EtecPresentismoEntity, Long> {

    Optional<EtecPresentismoEntity> findByAsignacionCargoIdAndAnhoAndMes(
            Long asignacionCargoId, Integer anho, Integer mes);

    List<EtecPresentismoEntity> findAllByAsignacionLegajoIdAndAnhoAndMesOrderByPresentismoIdAsc(
            Long legajoId, Integer anho, Integer mes);

    List<EtecPresentismoEntity> findAllByAnhoAndMesOrderByPresentismoIdAsc(Integer anho, Integer mes);

    boolean existsByAsignacionCargoIdAndAnhoAndMes(Long asignacionCargoId, Integer anho, Integer mes);

    void deleteAllByAsignacionCargoId(Long asignacionCargoId);
}
