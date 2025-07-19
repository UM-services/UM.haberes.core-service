package um.haberes.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import um.haberes.core.kotlin.model.LegajoInasistencia;

import java.util.Optional;

public interface LegajoInasistenciaRepository extends JpaRepository<LegajoInasistencia, Long> {

    Optional<LegajoInasistencia> findByLegajoIdAndAnhoAndMesAndFacultadIdAndGeograficaId(
            Long legajoId,
            Integer anho,
            Integer mes,
            Integer facultadId,
            Integer geograficaId
    );

}
