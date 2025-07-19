/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import org.springframework.stereotype.Service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import um.haberes.core.exception.CodigoGrupoException;
import um.haberes.core.kotlin.model.CodigoGrupo;
import um.haberes.core.repository.CodigoGrupoRepository;

/**
 * @author daniel
 *
 */
@Service
public class CodigoGrupoService {

	private final CodigoGrupoRepository repository;

	public CodigoGrupoService(CodigoGrupoRepository repository) {
		this.repository = repository;
	}

	@Cacheable("codigos_grupos")
	public List<CodigoGrupo> findAll() {
		return repository.findAll();
	}

	public List<CodigoGrupo> findAllByNoRemunerativo(Byte noRemunerativo) {
		return repository.findAllByNoRemunerativoOrderByCodigoId(noRemunerativo);
	}

	public List<CodigoGrupo> findAllByRemunerativo(Byte remunerativo) {
		return repository.findAllByRemunerativoOrderByCodigoId(remunerativo);
	}

	public List<CodigoGrupo> findAllByDeduccion(Byte deduccion) {
		return repository.findAllByDeduccionOrderByCodigoId(deduccion);
	}

	public CodigoGrupo findByCodigoId(Integer codigoId) {
		return repository.findByCodigoId(codigoId).orElseThrow(() -> new CodigoGrupoException(codigoId));
	}

	@CacheEvict(value = "codigos_grupos", allEntries = true)
	public CodigoGrupo add(CodigoGrupo codigoGrupo) {
		repository.save(codigoGrupo);
		return codigoGrupo;
	}

	@CacheEvict(value = "codigos_grupos", allEntries = true)
	public CodigoGrupo update(CodigoGrupo newCodigoGrupo, Integer codigoId) {
		return repository.findById(codigoId).map(codigoGrupo -> {
			codigoGrupo = new CodigoGrupo(codigoId, newCodigoGrupo.getRemunerativo(),
					newCodigoGrupo.getNoRemunerativo(), newCodigoGrupo.getDeduccion(), newCodigoGrupo.getTotal(),
					newCodigoGrupo.getCodigo());
			repository.save(codigoGrupo);
			return codigoGrupo;
		}).orElseThrow(() -> new CodigoGrupoException(codigoId));
	}

}
