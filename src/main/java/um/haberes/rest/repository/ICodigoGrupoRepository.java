/**
 * 
 */
package um.haberes.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import um.haberes.rest.model.CodigoGrupo;

/**
 * @author daniel
 *
 */
@Repository
public interface ICodigoGrupoRepository extends JpaRepository<CodigoGrupo, Integer> {

	public List<CodigoGrupo> findAllByNoRemunerativoOrderByCodigoId(Byte noRemunerativo);

	public List<CodigoGrupo> findAllByRemunerativoOrderByCodigoId(Byte remunerativo);

	public List<CodigoGrupo> findAllByDeduccionOrderByCodigoId(Byte deduccion);

	public Optional<CodigoGrupo> findByCodigoId(Integer codigoId);

}
