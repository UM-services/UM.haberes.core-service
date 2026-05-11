/**
 * 
 */
package um.haberes.core.hexagonal.facultad.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.hexagonal.facultad.infrastructure.persistence.entity.FacultadEntity;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaFacultadRepository extends JpaRepository<FacultadEntity, Integer> {

	public List<FacultadEntity> findAllByFacultadIdIn(List<Integer> ids);

	public Optional<FacultadEntity> findByFacultadId(Integer facultadId);

}
