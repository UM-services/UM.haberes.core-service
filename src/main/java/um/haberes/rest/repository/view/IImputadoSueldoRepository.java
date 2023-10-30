/**
 * 
 */
package um.haberes.rest.repository.view;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import um.haberes.rest.kotlin.model.view.ImputadoSueldo;

/**
 * @author daniel
 *
 */
public interface IImputadoSueldoRepository extends JpaRepository<ImputadoSueldo, String> {
	
	public List<ImputadoSueldo> findAllByAnhoAndMes(Integer anho, Integer mes);
	
}
