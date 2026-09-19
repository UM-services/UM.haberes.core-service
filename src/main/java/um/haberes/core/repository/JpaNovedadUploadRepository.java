/**
 * 
 */
package um.haberes.core.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import um.haberes.core.model.NovedadUploadEntity;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaNovedadUploadRepository extends JpaRepository<NovedadUploadEntity, Long> {

	public List<NovedadUploadEntity> findAllByAnhoAndMesAndPendiente(Integer anho, Integer mes, Byte pendiente, Sort sort);

	@Modifying
	public void deleteAllByPendiente(Byte pendiente);

}
