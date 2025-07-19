/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import um.haberes.core.kotlin.model.NovedadUpload;
import um.haberes.core.repository.NovedadUploadRepository;

/**
 * @author daniel
 *
 */
@Service
public class NovedadUploadService {

	@Autowired
	private NovedadUploadRepository repository;

	public List<NovedadUpload> findAllByPendiente(Integer anho, Integer mes, Byte pendiente) {
		return repository.findAllByAnhoAndMesAndPendiente(anho, mes, pendiente,
				Sort.by("codigoId").ascending().and(Sort.by("legajoId").ascending()));
	}

	@Transactional
	public List<NovedadUpload> saveAll(List<NovedadUpload> novedades) {
		return repository.saveAll(novedades);
	}

	@Transactional
	public void deleteAllByPendiente(Byte pendiente) {
		repository.deleteAllByPendiente(pendiente);
	}

	public NovedadUpload add(NovedadUpload novedadUpload) {
		novedadUpload = repository.save(novedadUpload);
		return novedadUpload;
	}

}
