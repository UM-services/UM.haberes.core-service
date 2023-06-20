/**
 * 
 */
package um.haberes.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.rest.exception.CodigoGrupoException;
import um.haberes.rest.kotlin.model.CodigoGrupo;
import um.haberes.rest.repository.ICodigoGrupoRepository;

/**
 * @author daniel
 *
 */
@Service
public class CodigoGrupoService {

	@Autowired
	private ICodigoGrupoRepository repository;

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

	public CodigoGrupo add(CodigoGrupo codigoGrupo) {
		repository.save(codigoGrupo);
		return codigoGrupo;
	}

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
