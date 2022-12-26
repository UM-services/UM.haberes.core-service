/**
 * 
 */
package ar.edu.um.haberes.rest.repository.view;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.um.haberes.rest.model.view.NovedadAcumulado;

/**
 * @author daniel
 *
 */
@Repository
public interface INovedadAcumuladoRepository extends JpaRepository<NovedadAcumulado, String> {

	public List<NovedadAcumulado> findAllByCodigoIdAndAnhoAndMes(Integer codigoId, Integer anho, Integer mes);

}
