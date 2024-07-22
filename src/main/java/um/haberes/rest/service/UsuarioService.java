/**
 * 
 */
package um.haberes.rest.service;

import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.rest.exception.UsuarioException;
import um.haberes.rest.kotlin.model.Usuario;
import um.haberes.rest.repository.IUsuarioRepository;
import um.haberes.rest.util.Tool;

/**
 * @author daniel
 *
 */
@Service
public class UsuarioService {

	private final IUsuarioRepository repository;

	@Autowired
	public UsuarioService(IUsuarioRepository repository) {
		this.repository = repository;
	}

	public Usuario findByLegajoId(Long legajoId) {
		return repository.findByLegajoId(legajoId).orElseThrow(() -> new UsuarioException(legajoId));
	}

	public Usuario updateLastLog(Long legajoId, Long build) {
		return repository.findByLegajoId(legajoId).map(usuario -> {
			usuario = new Usuario(legajoId, usuario.getPassword(), Tool.hourAbsoluteArgentina(), build,
					usuario.getUsuarioId(), usuario.getFacultadId());
			repository.save(usuario);
			return usuario;
		}).orElseThrow(() -> new UsuarioException(legajoId));
	}

	public Boolean isUserValid(Usuario usuario) {
		Optional<Usuario> user_opt = repository.findByLegajoIdAndPassword(usuario.getLegajoId(),
				DigestUtils.sha256Hex(usuario.getPassword()));
		return user_opt.isPresent();
	}

	public void setPassword(Usuario newUsuario) {
		Usuario usuario = repository.findByLegajoId(newUsuario.getLegajoId()).get();
		usuario = new Usuario(usuario.getLegajoId(), DigestUtils.sha256Hex(newUsuario.getPassword()),
				usuario.getLastLog(), newUsuario.getBuild(), usuario.getUsuarioId(), usuario.getFacultadId());
		repository.save(usuario);
	}

}
