package um.haberes.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import um.haberes.core.model.LegajoInasistenciaEntity;

import java.util.Optional;

public interface JpaLegajoInasistenciaRepository extends JpaRepository<LegajoInasistenciaEntity, Long> {

    Optional<LegajoInasistenciaEntity> findByLegajoIdAndAnhoAndMesAndFacultadIdAndGeograficaId(
            Long legajoId,
            Integer anho,
            Integer mes,
            Integer facultadId,
            Integer geograficaId
    );

}
