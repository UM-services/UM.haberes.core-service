/**
 * 
 */
package um.haberes.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.model.UsuarioEntity;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaUsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

	public Optional<UsuarioEntity> findByLegajoId(Long legajoId);

	public Optional<UsuarioEntity> findByLegajoIdAndPassword(Long legajoId, String password);

}
