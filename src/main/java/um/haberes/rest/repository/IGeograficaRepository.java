/**
 * 
 */
package um.haberes.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import um.haberes.rest.model.Geografica;

/**
 * @author dquinteros
 *
 */
@Repository
public interface IGeograficaRepository extends JpaRepository<Geografica, Integer> {

	public List<Geografica> findAllByGeograficaIdIn(List<Integer> ids);

	public Optional<Geografica> findByGeograficaId(Integer geograficaId);

}
