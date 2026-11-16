package um.haberes.core.hexagonal.etec.hora_escala.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import um.haberes.core.hexagonal.etec.hora_escala.infrastructure.persistence.entity.EscalaHoraEtecEntity;

public interface JpaEscalaHoraEtecRepository extends JpaRepository<EscalaHoraEtecEntity, Long> {

    List<EscalaHoraEtecEntity> findAllByControlEtecIdOrderByHorasDesdeAsc(Long controlEtecId);

    void deleteAllByControlEtecId(Long controlEtecId);
}
