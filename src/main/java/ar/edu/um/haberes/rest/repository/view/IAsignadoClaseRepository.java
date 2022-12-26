/**
 * 
 */
package ar.edu.um.haberes.rest.repository.view;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.um.haberes.rest.model.view.AsignadoClase;
import ar.edu.um.haberes.rest.model.view.pk.AsignadoClasePk;

/**
 * @author daniel
 *
 */
@Repository
public interface IAsignadoClaseRepository extends JpaRepository<AsignadoClase, AsignadoClasePk> {

	public List<AsignadoClase> findAllByDependenciaIdAndCargoClaseId(Integer dependenciaId, Long cargoClaseId,
			Sort sort);

}
