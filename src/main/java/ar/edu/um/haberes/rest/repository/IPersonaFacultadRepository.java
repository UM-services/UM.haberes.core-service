/**
 * 
 */
package ar.edu.um.haberes.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import ar.edu.um.haberes.rest.model.PersonaFacultad;

/**
 * @author daniel
 *
 */
@Repository
public interface IPersonaFacultadRepository extends JpaRepository<PersonaFacultad, Long> {

	public List<PersonaFacultad> findAllByFacultadId(Integer facultadId);

	public List<PersonaFacultad> findAllByLegajoId(Long legajoId);

	@Modifying
	public void deleteByLegajoIdAndFacultadId(Long legajoId, Integer facultadId);

}
