/**
 * 
 */
package ar.edu.um.haberes.rest.repository.view;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.um.haberes.rest.model.view.ImputadoSueldo;
import ar.edu.um.haberes.rest.model.view.pk.ImputadoSueldoPk;

/**
 * @author daniel
 *
 */
public interface IImputadoSueldoRepository extends JpaRepository<ImputadoSueldo, ImputadoSueldoPk> {
	
	public List<ImputadoSueldo> findAllByAnhoAndMes(Integer anho, Integer mes);
	
}
