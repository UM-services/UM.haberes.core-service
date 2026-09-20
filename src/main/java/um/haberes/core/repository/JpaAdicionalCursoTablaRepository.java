/**
 *
 */
package um.haberes.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Set;
import um.haberes.core.model.AdicionalCursoTablaEntity;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaAdicionalCursoTablaRepository extends JpaRepository<AdicionalCursoTablaEntity, Long> {

    Optional<AdicionalCursoTablaEntity> findByAdicionalCursoTablaId(Long adicionalCursoTablaId);

    Optional<AdicionalCursoTablaEntity> findByFacultadIdAndGeograficaIdIsNullAndPeriodoDesdeLessThanEqualAndPeriodoHastaGreaterThanEqual(
            Integer facultadId, Long periodoDesde, Long periodoHasta);

    Optional<AdicionalCursoTablaEntity> findByFacultadIdAndGeograficaIdAndPeriodoDesdeLessThanEqualAndPeriodoHastaGreaterThanEqual(Integer facultadId, Integer geograficaId, Long periodoDesde, Long periodoHasta);

    List<AdicionalCursoTablaEntity> findAllByFacultadIdInAndPeriodoDesdeLessThanEqualAndPeriodoHastaGreaterThanEqual(Set<Integer> facultadIds, Long periodoDesde, Long periodoHasta);

}
