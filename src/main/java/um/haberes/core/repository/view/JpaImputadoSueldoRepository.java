/**
 * 
 */
package um.haberes.core.repository.view;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import um.haberes.core.model.view.ImputadoSueldo;

/**
 * @author daniel
 *
 */
public interface JpaImputadoSueldoRepository extends JpaRepository<ImputadoSueldo, String> {
	
	public List<ImputadoSueldo> findAllByAnhoAndMes(Integer anho, Integer mes);
	
}
