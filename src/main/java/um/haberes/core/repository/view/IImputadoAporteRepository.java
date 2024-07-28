/**
 * 
 */
package um.haberes.core.repository.view;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import um.haberes.core.kotlin.model.view.ImputadoAporte;

/**
 * @author daniel
 *
 */
public interface IImputadoAporteRepository extends JpaRepository<ImputadoAporte, String> {
	
	public List<ImputadoAporte> findAllByAnhoAndMes(Integer anho, Integer mes);
	
}
