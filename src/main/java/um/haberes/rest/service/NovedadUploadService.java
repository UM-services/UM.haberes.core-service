/**
 * 
 */
package um.haberes.rest.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import um.haberes.rest.kotlin.model.NovedadUpload;
import um.haberes.rest.repository.INovedadUploadRepository;

/**
 * @author daniel
 *
 */
@Service
public class NovedadUploadService {

	@Autowired
	private INovedadUploadRepository repository;

	public List<NovedadUpload> findAllByPendiente(Integer anho, Integer mes, Byte pendiente) {
		return repository.findAllByAnhoAndMesAndPendiente(anho, mes, pendiente,
				Sort.by("codigoId").ascending().and(Sort.by("legajoId").ascending()));
	}

	@Transactional
	public void saveAll(List<NovedadUpload> novedades) {
		repository.saveAll(novedades);
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
