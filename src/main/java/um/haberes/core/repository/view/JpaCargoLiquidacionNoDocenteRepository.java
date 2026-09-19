/**
 * 
 */
package um.haberes.core.repository.view;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.model.view.CargoLiquidacionNoDocente;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaCargoLiquidacionNoDocenteRepository extends JpaRepository<CargoLiquidacionNoDocente, String> {

	public List<CargoLiquidacionNoDocente> findAllByAnhoAndMes(Integer anho, Integer mes);

}
