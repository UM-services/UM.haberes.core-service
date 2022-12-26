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

import ar.edu.um.haberes.rest.model.CursoCargoNovedad;

/**
 * @author daniel
 *
 */
@Repository
public interface ICursoCargoNovedadRepository extends JpaRepository<CursoCargoNovedad, Long> {

	public List<CursoCargoNovedad> findAllByAnhoAndMesAndAutorizadoAndRechazado(Integer anho, Integer mes,
			Byte autorizado, Byte rechazado);

	public List<CursoCargoNovedad> findAllByCursoIdAndAnhoAndMesAndAltaAndAutorizadoAndRechazado(Long cursoId,
			Integer anho, Integer mes, Byte alta, Byte autorizado, Byte rechazado, Sort sort);

	public List<CursoCargoNovedad> findAllByCursoIdAndAnhoAndMesAndCambioAndAutorizadoAndRechazado(Long cursoId,
			Integer anho, Integer mes, Byte cambio, Byte autorizado, Byte rechazado, Sort and);

	public List<CursoCargoNovedad> findAllByCursoIdAndAnhoAndMesAndAutorizadoAndLegajoId(Long cursoId, Integer anho,
			Integer mes, Byte autorizado, Long legajoId);

	public List<CursoCargoNovedad> findAllByCursoIdAndAnhoAndMesAndRechazadoAndLegajoId(Long cursoId, Integer anho,
			Integer mes, Byte rechazado, Long legajoId);

	public List<CursoCargoNovedad> findAllByCursoIdAndAnhoAndMesAndAutorizadoAndRechazadoAndLegajoId(Long cursoId,
			Integer anho, Integer mes, Byte autorizado, Byte rechazado, Long legajoId);

	public List<CursoCargoNovedad> findAllByAnhoAndMesAndAutorizadoAndRechazado(Integer anho, Integer mes, Byte autorizado,
			Byte rechazado, Sort sort);

	public List<CursoCargoNovedad> findAllByCursoIdAndAnhoAndMesAndBajaAndAutorizadoAndRechazado(Long cursoId,
			Integer anho, Integer mes, Byte baja, Byte autorizado, Byte rechazado, Sort sort);

	public List<CursoCargoNovedad> findAllByAnhoAndMesAndAutorizado(Integer anho, Integer mes, Byte autorizado, Sort sort);

	public List<CursoCargoNovedad> findAllByAnhoAndMesAndRechazado(Integer anho, Integer mes, Byte rechazado, Sort sort);

	public List<CursoCargoNovedad> findAllByAnhoAndMesAndBajaAndAutorizadoAndRechazado(Integer anho, Integer mes,
			Byte baja, Byte autorizado, Byte rechazado, Sort sort);

	public List<CursoCargoNovedad> findAllByAnhoAndMesAndBajaAndAutorizado(Integer anho, Integer mes, Byte baja,
			Byte autorizado, Sort sort);

	public List<CursoCargoNovedad> findAllByAnhoAndMesAndBajaAndRechazado(Integer anho, Integer mes, Byte baja,
			Byte rechazado, Sort sort);

	public Optional<CursoCargoNovedad> findByCursoCargoNovedadId(Long cursoCargoNovedadId);

	public Optional<CursoCargoNovedad> findByLegajoIdAndCursoIdAndAnhoAndMes(Long legajoId, Long cursoId, Integer anho,
			Integer mes);

	public Optional<CursoCargoNovedad> findByCursoIdAndAnhoAndMesAndCargoTipoIdAndLegajoId(Long cursoId, Integer anho,
			Integer mes, Integer cargoTipoId, Long legajoId);

	@Modifying
	public void deleteAllByLegajoIdAndCursoIdAndAnhoAndMesAndAutorizadoAndRechazado(Long legajoId, Long cursoId,
			Integer anho, Integer mes, Byte autorizado, Byte rechazado);

}
