/**
 * 
 */
package ar.edu.um.haberes.rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.um.haberes.rest.model.Lectivo;

/**
 * @author daniel
 *
 */
@Repository
public interface ILectivoRepository extends JpaRepository<Lectivo, Integer> {

	public Optional<Lectivo> findByLectivoId(Integer lectivoId);

}
