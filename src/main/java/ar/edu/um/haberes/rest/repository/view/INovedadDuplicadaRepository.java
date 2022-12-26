/**
 * 
 */
package ar.edu.um.haberes.rest.repository.view;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.um.haberes.rest.model.view.NovedadDuplicada;

/**
 * @author daniel
 *
 */
public interface INovedadDuplicadaRepository extends JpaRepository<NovedadDuplicada, String> {

	public List<NovedadDuplicada> findAllByAnhoAndMes(Integer anho, Integer mes);

}
