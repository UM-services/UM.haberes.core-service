package um.haberes.core.hexagonal.liquidaciones.acreditacion.infrastructure.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import um.haberes.core.hexagonal.liquidaciones.acreditacion.infrastructure.persistence.entity.AcreditacionEntity;

@Repository
public interface JpaAcreditacionRepository extends JpaRepository<AcreditacionEntity, Long> {

    Optional<AcreditacionEntity> findByAcreditacionId(Long acreditacionId);

    Optional<AcreditacionEntity> findByAnhoAndMes(Integer anho, Integer mes);

}
