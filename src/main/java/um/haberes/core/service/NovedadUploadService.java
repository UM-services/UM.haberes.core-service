/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import um.haberes.core.model.NovedadUploadEntity;
import um.haberes.core.repository.JpaNovedadUploadRepository;

/**
 * @author daniel
 *
 */
@Service
public class NovedadUploadService {

	@Autowired
	private JpaNovedadUploadRepository repository;

	public List<NovedadUploadEntity> findAllByPendiente(Integer anho, Integer mes, Byte pendiente) {
		return repository.findAllByAnhoAndMesAndPendiente(anho, mes, pendiente,
				Sort.by("codigoId").ascending().and(Sort.by("legajoId").ascending()));
	}

	@Transactional
	public List<NovedadUploadEntity> saveAll(List<NovedadUploadEntity> novedades) {
		return repository.saveAll(novedades);
	}

	@Transactional
	public void deleteAllByPendiente(Byte pendiente) {
		repository.deleteAllByPendiente(pendiente);
	}

	public NovedadUploadEntity add(NovedadUploadEntity novedadUpload) {
		novedadUpload = repository.save(novedadUpload);
		return novedadUpload;
	}

}
