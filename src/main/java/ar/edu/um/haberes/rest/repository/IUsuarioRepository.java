/**
 * 
 */
package ar.edu.um.haberes.rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.um.haberes.rest.model.Usuario;

/**
 * @author daniel
 *
 */
@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

	public Optional<Usuario> findByLegajoId(Long legajoId);

	public Optional<Usuario> findByLegajoIdAndPassword(Long legajoId, String password);

}
