/**
 * 
 */
package um.haberes.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.rest.exception.SeguridadSocialNotFoundException;
import um.haberes.rest.model.SeguridadSocial;
import um.haberes.rest.repository.ISeguridadSocialRepository;

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
				.orElseThrow(() -> new SeguridadSocialNotFoundException(anho, mes));
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
		}).orElseThrow(() -> new SeguridadSocialNotFoundException(seguridadSocialId));
	}

}
