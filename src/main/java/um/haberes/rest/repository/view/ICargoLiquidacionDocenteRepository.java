/**
 * 
 */
package um.haberes.rest.repository.view;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.rest.kotlin.model.view.CargoLiquidacionDocente;

/**
 * @author daniel
 *
 */
@Repository
public interface ICargoLiquidacionDocenteRepository extends JpaRepository<CargoLiquidacionDocente, String> {

	public List<CargoLiquidacionDocente> findAllByAnhoAndMes(Integer anho, Integer mes);

}
