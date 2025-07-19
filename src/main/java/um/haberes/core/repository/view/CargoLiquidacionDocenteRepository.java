/**
 * 
 */
package um.haberes.core.repository.view;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.view.CargoLiquidacionDocente;

/**
 * @author daniel
 *
 */
@Repository
public interface CargoLiquidacionDocenteRepository extends JpaRepository<CargoLiquidacionDocente, String> {

	public List<CargoLiquidacionDocente> findAllByAnhoAndMes(Integer anho, Integer mes);

}
