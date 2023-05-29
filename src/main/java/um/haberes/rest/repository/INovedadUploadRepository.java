/**
 * 
 */
package um.haberes.rest.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import um.haberes.rest.model.NovedadUpload;

/**
 * @author daniel
 *
 */
@Repository
public interface INovedadUploadRepository extends JpaRepository<NovedadUpload, Long> {

	public List<NovedadUpload> findAllByAnhoAndMesAndPendiente(Integer anho, Integer mes, Byte pendiente, Sort sort);

	@Modifying
	public void deleteAllByPendiente(Byte pendiente);

}
