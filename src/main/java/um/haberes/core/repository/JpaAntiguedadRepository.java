/**
 * 
 */
package um.haberes.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import um.haberes.core.model.AntiguedadEntity;

/**
 * @author daniel
 *
 */
public interface JpaAntiguedadRepository extends JpaRepository<AntiguedadEntity, Long> {

	public List<AntiguedadEntity> findAllByAnhoAndMes(Integer anho, Integer mes, Pageable pageable);

	public Optional<AntiguedadEntity> findByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

	public Optional<AntiguedadEntity> findByAntiguedadId(Long antiguedadId);

}
