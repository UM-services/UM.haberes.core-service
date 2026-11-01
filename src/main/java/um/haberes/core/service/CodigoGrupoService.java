/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import org.springframework.stereotype.Service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import um.haberes.core.exception.CodigoGrupoException;
import um.haberes.core.model.CodigoGrupoEntity;
import um.haberes.core.repository.JpaCodigoGrupoRepository;

/**
 * @author daniel
 *
 */
@Service
public class CodigoGrupoService {

	private final JpaCodigoGrupoRepository repository;

	public CodigoGrupoService(JpaCodigoGrupoRepository repository) {
		this.repository = repository;
	}

	@Cacheable("codigos_grupos")
	public List<CodigoGrupoEntity> findAll() {
		return repository.findAll();
	}

	public List<CodigoGrupoEntity> findAllByNoRemunerativo(Byte noRemunerativo) {
		return repository.findAllByNoRemunerativoOrderByCodigoId(noRemunerativo);
	}

	public List<CodigoGrupoEntity> findAllByRemunerativo(Byte remunerativo) {
		return repository.findAllByRemunerativoOrderByCodigoId(remunerativo);
	}

	public List<CodigoGrupoEntity> findAllByDeduccion(Byte deduccion) {
		return repository.findAllByDeduccionOrderByCodigoId(deduccion);
	}

	public CodigoGrupoEntity findByCodigoId(Integer codigoId) {
		return repository.findByCodigoId(codigoId).orElseThrow(() -> new CodigoGrupoException(codigoId));
	}

	@CacheEvict(value = "codigos_grupos", allEntries = true)
	public CodigoGrupoEntity add(CodigoGrupoEntity codigoGrupo) {
		repository.save(codigoGrupo);
		return codigoGrupo;
	}

	@CacheEvict(value = "codigos_grupos", allEntries = true)
	public CodigoGrupoEntity update(CodigoGrupoEntity newCodigoGrupo, Integer codigoId) {
		return repository.findById(codigoId).map(codigoGrupo -> {
			codigoGrupo = new CodigoGrupoEntity(codigoId, newCodigoGrupo.getRemunerativo(),
					newCodigoGrupo.getNoRemunerativo(), newCodigoGrupo.getDeduccion(), newCodigoGrupo.getTotal(),
					newCodigoGrupo.getCodigo());
			repository.save(codigoGrupo);
			return codigoGrupo;
		}).orElseThrow(() -> new CodigoGrupoException(codigoId));
	}

}
