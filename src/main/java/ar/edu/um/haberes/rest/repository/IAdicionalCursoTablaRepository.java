/**
 * 
 */
package ar.edu.um.haberes.rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.um.haberes.rest.model.AdicionalCursoTabla;

/**
 * @author daniel
 *
 */
@Repository
public interface IAdicionalCursoTablaRepository extends JpaRepository<AdicionalCursoTabla, Long> {

	public Optional<AdicionalCursoTabla> findByAdicionalCursoTablaId(Long adicionalCursoTablaId);

	public Optional<AdicionalCursoTabla> findByFacultadIdAndPeriodoDesdeLessThanEqualAndPeriodoHastaGreaterThanEqual(
			Integer facultadId, Long periodoDesde, Long periodoHasta);

}
