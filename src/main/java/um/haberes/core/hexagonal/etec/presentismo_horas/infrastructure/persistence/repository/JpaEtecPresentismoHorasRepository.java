package um.haberes.core.hexagonal.etec.presentismo_horas.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import um.haberes.core.hexagonal.etec.presentismo_horas.infrastructure.persistence.entity.EtecPresentismoHorasEntity;

@Repository
public interface JpaEtecPresentismoHorasRepository
        extends JpaRepository<EtecPresentismoHorasEntity, Long> {

    Optional<EtecPresentismoHorasEntity> findByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

    List<EtecPresentismoHorasEntity> findAllByAnhoAndMesOrderByLegajoIdAsc(Integer anho, Integer mes);

    boolean existsByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);
}
