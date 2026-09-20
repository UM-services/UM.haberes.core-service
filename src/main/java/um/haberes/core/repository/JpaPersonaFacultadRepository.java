/**
 * 
 */
package um.haberes.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import um.haberes.core.model.PersonaFacultadEntity;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaPersonaFacultadRepository extends JpaRepository<PersonaFacultadEntity, Long> {

	public List<PersonaFacultadEntity> findAllByFacultadId(Integer facultadId);

	public List<PersonaFacultadEntity> findAllByLegajoId(Long legajoId);

	@Modifying
	public void deleteByLegajoIdAndFacultadId(Long legajoId, Integer facultadId);

}
