/**
 * 
 */
package ar.edu.um.haberes.rest.repository.view;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.um.haberes.rest.model.view.ImputadoAporte;
import ar.edu.um.haberes.rest.model.view.pk.ImputadoAportePk;

/**
 * @author daniel
 *
 */
public interface IImputadoAporteRepository extends JpaRepository<ImputadoAporte, ImputadoAportePk> {
	
	public List<ImputadoAporte> findAllByAnhoAndMes(Integer anho, Integer mes);
	
}
