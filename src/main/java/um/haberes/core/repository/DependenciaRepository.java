/**
 * 
 */
package um.haberes.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Set;
import um.haberes.core.kotlin.model.Dependencia;

/**
 * @author daniel
 *
 */
@Repository
public interface DependenciaRepository extends JpaRepository<Dependencia, Integer> {

	public List<Dependencia> findAllByFacultadIdAndGeograficaId(Integer facultadId, Integer geograficaId);

	public Optional<Dependencia> findFirstByFacultadIdAndGeograficaId(Integer facultadId, Integer geograficaId);

	public Optional<Dependencia> findByDependenciaId(Integer dependenciaId);

	public List<Dependencia> findAllByDependenciaIdIn(Set<Integer> dependenciaIds);

}
