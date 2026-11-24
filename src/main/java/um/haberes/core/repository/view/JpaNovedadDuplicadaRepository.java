/**
 * 
 */
package um.haberes.core.repository.view;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.model.view.NovedadDuplicada;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaNovedadDuplicadaRepository extends JpaRepository<NovedadDuplicada, String>,
		JpaNovedadDuplicadaRepositoryCustom {

}
