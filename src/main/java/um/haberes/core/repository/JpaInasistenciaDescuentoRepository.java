package um.haberes.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import um.haberes.core.model.InasistenciaDescuentoEntity;

import java.util.Optional;

public interface JpaInasistenciaDescuentoRepository extends JpaRepository<InasistenciaDescuentoEntity, Long> {

    Optional<InasistenciaDescuentoEntity> findByFacultadIdAndGeograficaIdAndDesdeGreaterThanEqualAndHastaLessThanEqual(
            Integer facultadId,
            Integer geograficaId,
            Integer inasistenciasMin,
            Integer inasistenciasMay
    );


}
