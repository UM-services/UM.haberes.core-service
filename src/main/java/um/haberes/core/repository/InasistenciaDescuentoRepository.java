package um.haberes.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import um.haberes.core.kotlin.model.InasistenciaDescuento;

import java.util.Optional;

public interface InasistenciaDescuentoRepository extends JpaRepository<InasistenciaDescuento, Long> {

    Optional<InasistenciaDescuento> findByFacultadIdAndGeograficaIdAndDesdeGreaterThanEqualAndHastaLessThanEqual(
            Integer facultadId,
            Integer geograficaId,
            Integer inasistenciasMin,
            Integer inasistenciasMay
    );


}
