/**
 * 
 */
package ar.edu.um.haberes.rest.repository.view;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.um.haberes.rest.model.view.TotalMensual;

/**
 * @author daniel
 *
 */
public interface ITotalMensualRepository extends JpaRepository<TotalMensual, String> {

	public List<TotalMensual> findAllByAnhoAndMes(Integer anho, Integer mes);

	public Optional<TotalMensual> findByAnhoAndMesAndCodigoId(Integer anho, Integer mes, Integer codigoId);

}
