/**
 *
 */
package um.haberes.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Set;
import um.haberes.core.kotlin.model.AdicionalCursoTabla;

/**
 * @author daniel
 *
 */
@Repository
public interface AdicionalCursoTablaRepository extends JpaRepository<AdicionalCursoTabla, Long> {

    Optional<AdicionalCursoTabla> findByAdicionalCursoTablaId(Long adicionalCursoTablaId);

    Optional<AdicionalCursoTabla> findByFacultadIdAndGeograficaIdIsNullAndPeriodoDesdeLessThanEqualAndPeriodoHastaGreaterThanEqual(
            Integer facultadId, Long periodoDesde, Long periodoHasta);

    Optional<AdicionalCursoTabla> findByFacultadIdAndGeograficaIdAndPeriodoDesdeLessThanEqualAndPeriodoHastaGreaterThanEqual(Integer facultadId, Integer geograficaId, Long periodoDesde, Long periodoHasta);

    List<AdicionalCursoTabla> findAllByFacultadIdInAndPeriodoDesdeLessThanEqualAndPeriodoHastaGreaterThanEqual(Set<Integer> facultadIds, Long periodoDesde, Long periodoHasta);

}
