/**
 * 
 */
package um.haberes.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import um.haberes.rest.model.Anotador;

/**
 * @author daniel
 *
 */
@Repository
public interface IAnotadorRepository extends JpaRepository<Anotador, Long> {

	public List<Anotador> findAllByLegajoIdOrderByAnotadorIdDesc(Long legajoId);

	public List<Anotador> findAllByAnhoAndMesAndAutorizadoAndRechazado(Integer anho, Integer mes, Byte autorizado,
			Byte rechazado);

	public List<Anotador> findTop1000ByAnhoAndMes(Integer anho, Integer mes);

	public List<Anotador> findTop1000ByAnhoAndMesAndAutorizadoAndRechazadoAndLegajoIdInOrderByAnotadorId(Integer anho,
			Integer mes, Byte autorizado, Byte rechazado, List<Long> legajos);

	public List<Anotador> findTop1000ByAnhoAndMesAndLegajoIdInOrderByAnotadorIdDesc(Integer anho, Integer mes,
			List<Long> legajos);

	public List<Anotador> findTop1000ByAnhoAndMesAndAutorizadoAndRechazadoAndFacultadIdOrderByAnotadorId(Integer anho,
			Integer mes, Byte autorizado, Byte rechazado, Integer facultadId);

	public List<Anotador> findTop1000ByAnhoAndMesAndFacultadIdOrderByAnotadorIdDesc(Integer anho, Integer mes,
			Integer facultadId);

	public Optional<Anotador> findByAnotadorId(Long anotadorId);

}
