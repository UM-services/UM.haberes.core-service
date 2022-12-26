/**
 * 
 */
package ar.edu.um.haberes.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.um.haberes.rest.model.Dependencia;

/**
 * @author daniel
 *
 */
@Repository
public interface IDependenciaRepository extends JpaRepository<Dependencia, Integer> {

	public List<Dependencia> findAllByFacultadIdAndGeograficaId(Integer facultadId, Integer geograficaId);

	public Optional<Dependencia> findFirstByFacultadIdAndGeograficaId(Integer facultadId, Integer geograficaId);

	public Optional<Dependencia> findByDependenciaId(Integer dependenciaId);

}
