/**
 * 
 */
package ar.edu.um.haberes.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import ar.edu.um.haberes.rest.model.AdicionalCursoRango;

/**
 * @author daniel
 *
 */
@Repository
public interface IAdicionalCursoRangoRepository extends JpaRepository<AdicionalCursoRango, Long> {

	public List<AdicionalCursoRango> findAllByAdicionalCursoTablaIdOrderByHorasDesde(Long adicionalCursoTablaId);

	public Optional<AdicionalCursoRango> findByAdicionalCursoRangoId(Long adicionalCursoRangoId);

	@Modifying
	public void deleteByAdicionalCursoRangoId(Long adicionalCursoRangoId);

}
