/**
 * 
 */
package um.haberes.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.model.CodigoGrupoEntity;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaCodigoGrupoRepository extends JpaRepository<CodigoGrupoEntity, Integer> {

	public List<CodigoGrupoEntity> findAllByNoRemunerativoOrderByCodigoId(Byte noRemunerativo);

	public List<CodigoGrupoEntity> findAllByRemunerativoOrderByCodigoId(Byte remunerativo);

	public List<CodigoGrupoEntity> findAllByDeduccionOrderByCodigoId(Byte deduccion);

	public Optional<CodigoGrupoEntity> findByCodigoId(Integer codigoId);

}
