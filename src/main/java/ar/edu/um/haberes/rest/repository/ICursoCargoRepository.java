/**
 * 
 */
package ar.edu.um.haberes.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import ar.edu.um.haberes.rest.model.CursoCargo;

/**
 * @author daniel
 *
 */
@Repository
public interface ICursoCargoRepository extends JpaRepository<CursoCargo, Long> {

	public List<CursoCargo> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes, Sort sort);

	public List<CursoCargo> findAllByLegajoIdAndAnhoAndMesAndDesarraigo(Long legajoId, Integer anho, Integer mes,
			Byte desarraigo);

	public List<CursoCargo> findAllByLegajoIdAndAnhoAndMesAndCargoTipoId(Long legajoId, Integer anho,
			Integer mes, Integer cargoTipoId);

	public List<CursoCargo> findAllByCursoIdAndAnhoAndMes(Long cursoId, Integer anho, Integer mes, Sort sort);

	public List<CursoCargo> findTopByCursoId(Long cursoId);

	public List<CursoCargo> findAllByAnhoAndMes(Integer anho, Integer mes);

	public List<CursoCargo> findTopByAnhoAndMes(Integer anho, Integer mes);

	public List<CursoCargo> findAllByAnhoAndMesAndDesarraigo(Integer anho, Integer mes, Byte desarraigo);

	public Optional<CursoCargo> findByCursoIdAndAnhoAndMesAndCargoTipoIdAndLegajoId(Long cursoId, Integer anho,
			Integer mes, Integer cargoTipoId, Long legajoId);

	public Optional<CursoCargo> findByCursoCargoId(Long cursoCargoId);

	public Optional<CursoCargo> findByCursoIdAndAnhoAndMesAndLegajoId(Long cursoId, Integer anho, Integer mes,
			Long legajoId);

	@Modifying
	public void deleteByCursoCargoId(Long cursoCargoId);

	@Modifying
	public void deleteByCursoIdAndAnhoAndMesAndCargoTipoIdAndLegajoId(Long cursoId, Integer anho, Integer mes,
			Integer cargoTipoId, Long legajoId);

}
