/**
 * 
 */
package um.haberes.core.repository.view;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.view.TotalSalida;

/**
 * @author daniel
 *
 */
@Repository
public interface ITotalSalidaRepository extends JpaRepository<TotalSalida, String> {

	public Optional<TotalSalida> findByAnhoAndMes(Integer anho, Integer mes);

}
