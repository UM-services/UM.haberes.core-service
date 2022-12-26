/**
 * 
 */
package ar.edu.um.haberes.rest.repository.view;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.um.haberes.rest.model.view.CodigoPeriodo;
import ar.edu.um.haberes.rest.model.view.pk.CodigoPeriodoPK;

/**
 * @author daniel
 *
 */
@Repository
public interface ICodigoPeriodoRepository extends JpaRepository<CodigoPeriodo, CodigoPeriodoPK> {

	public List<CodigoPeriodo> findAllByAnhoAndMes(Integer anho, Integer mes);

}
