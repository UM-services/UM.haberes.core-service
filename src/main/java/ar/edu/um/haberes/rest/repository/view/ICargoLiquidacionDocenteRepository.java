/**
 * 
 */
package ar.edu.um.haberes.rest.repository.view;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.um.haberes.rest.model.view.CargoLiquidacionDocente;

/**
 * @author daniel
 *
 */
@Repository
public interface ICargoLiquidacionDocenteRepository extends JpaRepository<CargoLiquidacionDocente, String> {

	public List<CargoLiquidacionDocente> findAllByAnhoAndMes(Integer anho, Integer mes);

}
