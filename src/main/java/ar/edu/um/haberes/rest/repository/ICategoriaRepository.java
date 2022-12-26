/**
 * 
 */
package ar.edu.um.haberes.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.um.haberes.rest.model.Categoria;

/**
 * @author daniel
 *
 */
@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Integer> {

	public List<Categoria> findAllByDocente(Byte docente, Sort sort);

	public List<Categoria> findAllByNoDocente(Byte noDocente, Sort sort);

	public List<Categoria> findAllByCategoriaIdIn(List<Integer> categoriaIds);

	public List<Categoria> findAllByCategoriaIdNotIn(List<Integer> categoriaIds);

	public Optional<Categoria> findByCategoriaId(Integer categoriaId);

	public Optional<Categoria> findTopByOrderByCategoriaIdDesc();

}
