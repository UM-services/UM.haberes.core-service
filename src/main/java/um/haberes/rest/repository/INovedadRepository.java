/**
 * 
 */
package um.haberes.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import um.haberes.rest.model.Novedad;

/**
 * @author daniel
 *
 */
@Repository
public interface INovedadRepository extends JpaRepository<Novedad, Long> {

	public List<Novedad> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

	public List<Novedad> findAllByCodigoIdAndAnhoAndMes(Integer codigoId, Integer anho, Integer mes, Sort sort);

	public List<Novedad> findAllByImportadoAndAnhoAndMes(Byte importado, Integer anho, Integer mes);

	public List<Novedad> findAllByLegajoIdAndAnhoAndMesAndCodigoId(Long legajoId, Integer anho, Integer mes,
			Integer codigoId);

	public Optional<Novedad> findByNovedadId(Long novedadId);

	public Optional<Novedad> findByLegajoIdAndAnhoAndMesAndCodigoIdAndDependenciaId(Long legajoId, Integer anho,
			Integer mes, Integer codigoId, Integer dependenciaId);

	@Modifying
	public void deleteAllByAnhoAndMes(Integer anho, Integer mes);

}
