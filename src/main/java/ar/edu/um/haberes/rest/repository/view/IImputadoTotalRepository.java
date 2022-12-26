/**
 * 
 */
package ar.edu.um.haberes.rest.repository.view;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.um.haberes.rest.model.view.ImputadoTotal;
import ar.edu.um.haberes.rest.model.view.pk.ImputadoTotalPk;

/**
 * @author daniel
 *
 */
public interface IImputadoTotalRepository extends JpaRepository<ImputadoTotal, ImputadoTotalPk> {
	
	public Optional<ImputadoTotal> findByAnhoAndMes(Integer anho, Integer mes);

}
