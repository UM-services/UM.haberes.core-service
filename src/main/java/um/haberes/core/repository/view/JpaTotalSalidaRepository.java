/**
 * 
 */
package um.haberes.core.repository.view;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.model.view.TotalSalida;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaTotalSalidaRepository extends JpaRepository<TotalSalida, String> {

	public Optional<TotalSalida> findByAnhoAndMes(Integer anho, Integer mes);

}
