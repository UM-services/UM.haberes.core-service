/**
 * 
 */
package um.haberes.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.SeguridadSocialException;
import um.haberes.core.kotlin.model.SeguridadSocial;
import um.haberes.core.repository.ISeguridadSocialRepository;

/**
 * @author daniel
 *
 */
@Service
public class SeguridadSocialService {

	@Autowired
	private ISeguridadSocialRepository repository;

	public SeguridadSocial findByUnique(Integer anho, Integer mes) {
		return repository.findByAnhoAndMes(anho, mes)
				.orElseThrow(() -> new SeguridadSocialException(anho, mes));
	}

	public SeguridadSocial add(SeguridadSocial seguridadSocial) {
		seguridadSocial = repository.save(seguridadSocial);
		return seguridadSocial;
	}

	public SeguridadSocial update(SeguridadSocial newSeguridadSocial, Long seguridadSocialId) {
		return repository.findBySeguridadSocialId(seguridadSocialId).map(seguridadSocial -> {
			seguridadSocial = new SeguridadSocial(seguridadSocialId, newSeguridadSocial.getAnho(),
					newSeguridadSocial.getMes(), newSeguridadSocial.getCc351(), newSeguridadSocial.getCc301(),
					newSeguridadSocial.getCc352(), newSeguridadSocial.getCc302(), newSeguridadSocial.getCc312(),
					newSeguridadSocial.getCc028());
			seguridadSocial = repository.save(seguridadSocial);
			return seguridadSocial;
		}).orElseThrow(() -> new SeguridadSocialException(seguridadSocialId));
	}

}
