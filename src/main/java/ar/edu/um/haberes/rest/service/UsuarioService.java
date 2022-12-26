/**
 * 
 */
package ar.edu.um.haberes.rest.service;

import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.um.haberes.rest.exception.UsuarioNotFoundException;
import ar.edu.um.haberes.rest.model.Usuario;
import ar.edu.um.haberes.rest.repository.IUsuarioRepository;
import ar.edu.um.haberes.rest.util.Tool;

/**
 * @author daniel
 *
 */
@Service
public class UsuarioService {

	@Autowired
	private IUsuarioRepository repository;

	public Usuario findByLegajoId(Long legajoId) {
		return repository.findByLegajoId(legajoId).orElseThrow(() -> new UsuarioNotFoundException(legajoId));
	}

	public Usuario updateLastLog(Long legajoId) {
		return repository.findByLegajoId(legajoId).map(usuario -> {
			usuario = new Usuario(legajoId, usuario.getPassword(), Tool.hourAbsoluteArgentina(),
					usuario.getUsuarioId());
			repository.save(usuario);
			return usuario;
		}).orElseThrow(() -> new UsuarioNotFoundException(legajoId));
	}

	public Boolean isUserValid(Usuario usuario) {
		Optional<Usuario> user_opt = repository.findByLegajoIdAndPassword(usuario.getLegajoId(),
				DigestUtils.sha256Hex(usuario.getPassword()));
		return user_opt.isPresent();
	}

	public void setPassword(Usuario newusuario) {
		Usuario usuario = repository.findByLegajoId(newusuario.getLegajoId()).get();
		usuario = new Usuario(usuario.getLegajoId(), DigestUtils.sha256Hex(newusuario.getPassword()),
				usuario.getLastLog(), usuario.getUsuarioId());
		repository.save(usuario);
	}

}
