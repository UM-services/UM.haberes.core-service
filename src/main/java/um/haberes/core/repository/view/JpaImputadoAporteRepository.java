/**
 * 
 */
package um.haberes.core.repository.view;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import um.haberes.core.model.view.ImputadoAporte;

/**
 * @author daniel
 *
 */
public interface JpaImputadoAporteRepository extends JpaRepository<ImputadoAporte, String> {
	
	public List<ImputadoAporte> findAllByAnhoAndMes(Integer anho, Integer mes);
	
}
