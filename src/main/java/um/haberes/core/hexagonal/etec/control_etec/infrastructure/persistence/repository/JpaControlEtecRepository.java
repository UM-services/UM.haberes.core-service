package um.haberes.core.hexagonal.etec.control_etec.infrastructure.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import um.haberes.core.hexagonal.etec.control_etec.infrastructure.persistence.entity.ControlEtecEntity;

public interface JpaControlEtecRepository extends JpaRepository<ControlEtecEntity, Long> {

    Optional<ControlEtecEntity> findByAnhoAndMes(Integer anho, Integer mes);

    boolean existsByAnhoAndMes(Integer anho, Integer mes);
}
