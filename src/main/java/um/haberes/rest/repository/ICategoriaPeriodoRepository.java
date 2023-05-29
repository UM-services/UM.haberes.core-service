/**
 * 
 */
package um.haberes.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import um.haberes.rest.model.CategoriaPeriodo;

/**
 * @author daniel
 *
 */
@Repository
public interface ICategoriaPeriodoRepository extends JpaRepository<CategoriaPeriodo, Long> {

	public List<CategoriaPeriodo> findAllByAnhoAndMes(Integer anho, Integer mes);

	public Optional<CategoriaPeriodo> findByCategoriaPeriodoId(Long categoriaPeriodoId);

	public Optional<CategoriaPeriodo> findByCategoriaIdAndAnhoAndMes(Integer categoriaId, Integer anho, Integer mes);

	@Modifying
	public void deleteAllByAnhoAndMes(Integer anho, Integer mes);

}
