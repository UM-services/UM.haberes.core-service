/**
 * 
 */
package um.haberes.core.service;

import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.UsuarioException;
import um.haberes.core.model.UsuarioEntity;
import um.haberes.core.repository.JpaUsuarioRepository;
import um.haberes.core.util.Tool;

/**
 * @author daniel
 *
 */
@Service
public class UsuarioService {

	private final JpaUsuarioRepository repository;

	@Autowired
	public UsuarioService(JpaUsuarioRepository repository) {
		this.repository = repository;
	}

	public UsuarioEntity findByLegajoId(Long legajoId) {
		return repository.findByLegajoId(legajoId).orElseThrow(() -> new UsuarioException(legajoId));
	}

	public UsuarioEntity updateLastLog(Long legajoId, Long build) {
		return repository.findByLegajoId(legajoId).map(usuario -> {
			usuario = new UsuarioEntity(legajoId, usuario.getPassword(), Tool.hourAbsoluteArgentina(), build,
					usuario.getUsuarioId(), usuario.getFacultadId());
			repository.save(usuario);
			return usuario;
		}).orElseThrow(() -> new UsuarioException(legajoId));
	}

	public Boolean isUserValid(UsuarioEntity usuario) {
		Optional<UsuarioEntity> user_opt = repository.findByLegajoIdAndPassword(usuario.getLegajoId(),
				DigestUtils.sha256Hex(usuario.getPassword()));
		return user_opt.isPresent();
	}

	public void setPassword(UsuarioEntity newUsuario) {
		UsuarioEntity usuario = repository.findByLegajoId(newUsuario.getLegajoId()).get();
		usuario = new UsuarioEntity(usuario.getLegajoId(), DigestUtils.sha256Hex(newUsuario.getPassword()),
				usuario.getLastLog(), newUsuario.getBuild(), usuario.getUsuarioId(), usuario.getFacultadId());
		repository.save(usuario);
	}

}
