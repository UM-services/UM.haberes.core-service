/**
 * 
 */
package ar.edu.um.haberes.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.um.haberes.rest.model.Designacion;

/**
 * @author daniel
 *
 */
@Repository
public interface IDesignacionRepository extends JpaRepository<Designacion, Integer> {

	public List<Designacion> findAllByCategoriaIdNotNull();

	public Optional<Designacion> findByDesignacionTipoIdAndCargoTipoIdAndAnualAndSemestral(Integer designaciontipoId,
			Integer cargotipoId, Byte anual, Byte semestral);

}
