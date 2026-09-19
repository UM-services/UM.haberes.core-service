/**
 * 
 */
package um.haberes.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.SeguridadSocialException;
import um.haberes.core.model.SeguridadSocialEntity;
import um.haberes.core.repository.JpaSeguridadSocialRepository;

/**
 * @author daniel
 *
 */
@Service
public class SeguridadSocialService {

	@Autowired
	private JpaSeguridadSocialRepository repository;

	public SeguridadSocialEntity findByUnique(Integer anho, Integer mes) {
		return repository.findByAnhoAndMes(anho, mes)
				.orElseThrow(() -> new SeguridadSocialException(anho, mes));
	}

	public SeguridadSocialEntity add(SeguridadSocialEntity seguridadSocial) {
		seguridadSocial = repository.save(seguridadSocial);
		return seguridadSocial;
	}

	public SeguridadSocialEntity update(SeguridadSocialEntity newSeguridadSocial, Long seguridadSocialId) {
		return repository.findBySeguridadSocialId(seguridadSocialId).map(seguridadSocial -> {
			seguridadSocial = new SeguridadSocialEntity(seguridadSocialId, newSeguridadSocial.getAnho(),
					newSeguridadSocial.getMes(), newSeguridadSocial.getCc351(), newSeguridadSocial.getCc301(),
					newSeguridadSocial.getCc352(), newSeguridadSocial.getCc302(), newSeguridadSocial.getCc312(),
					newSeguridadSocial.getCc028());
			seguridadSocial = repository.save(seguridadSocial);
			return seguridadSocial;
		}).orElseThrow(() -> new SeguridadSocialException(seguridadSocialId));
	}

}
