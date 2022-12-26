/**
 * 
 */
package ar.edu.um.haberes.rest.repository.view;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.um.haberes.rest.model.view.CargoLiquidacionNoDocente;

/**
 * @author daniel
 *
 */
@Repository
public interface ICargoLiquidacionNoDocenteRepository extends JpaRepository<CargoLiquidacionNoDocente, String> {

	public List<CargoLiquidacionNoDocente> findAllByAnhoAndMes(Integer anho, Integer mes);

}
