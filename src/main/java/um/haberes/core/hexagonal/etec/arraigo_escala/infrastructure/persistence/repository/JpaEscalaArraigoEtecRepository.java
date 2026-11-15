package um.haberes.core.hexagonal.etec.arraigo_escala.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import um.haberes.core.hexagonal.etec.arraigo_escala.infrastructure.persistence.entity.EscalaArraigoEtecEntity;

public interface JpaEscalaArraigoEtecRepository extends JpaRepository<EscalaArraigoEtecEntity, Long> {

    List<EscalaArraigoEtecEntity> findAllByOrderByAniosMinimosAsc();
}
