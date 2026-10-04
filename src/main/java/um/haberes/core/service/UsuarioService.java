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
import um.haberes.core.model.dto.CambiarClaveRequest;
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

	/**
	 * Cambio de clave con verificacion de la anterior server-side (espejo del
	 * use case change-password de tesoreria-core, sin guarda de admin y sin
	 * chequeo de clave duplicada, por decisiones del plan
	 * PLANIFICACION_CAMBIO_CLAVE_HABERES.md). A diferencia de isUserValid
	 * (que no se toca), aqui si se normaliza con trim() antes de hashear,
	 * igual que tesoreria-core.
	 *
	 * @param request legajoId + claves en claro (legajoId puede venir null -> NO Encontrado)
	 * @throws IllegalArgumentException con el mensaje de negocio que muestra el frontend
	 */
	public void cambiarClave(CambiarClaveRequest request) {
		if (request.getNewPassword() == null || request.getNewPassword().trim().isEmpty()) {
			throw new IllegalArgumentException("ERROR: Falta CLAVE . . .");
		}

		if (request.getReClaveNueva() != null
				&& !request.getNewPassword().trim().equals(request.getReClaveNueva().trim())) {
			throw new IllegalArgumentException("ERROR: Claves NO Coinciden");
		}

		if (request.getCurrentPassword() == null || request.getCurrentPassword().trim().isEmpty()) {
			throw new IllegalArgumentException("ERROR: Falta Clave Anterior");
		}

		UsuarioEntity usuario = request.getLegajoId() == null ? null
				: repository.findByLegajoId(request.getLegajoId()).orElse(null);
		if (usuario == null) {
			throw new IllegalArgumentException("ERROR: Usuario NO Encontrado");
		}

		String currentHashed = DigestUtils.sha256Hex(request.getCurrentPassword().trim());
		String dbPassword = usuario.getPassword() != null ? usuario.getPassword().trim() : "";
		if (!currentHashed.equals(dbPassword)) {
			throw new IllegalArgumentException("ERROR: Usuario NO Autenticado");
		}

		String newHashed = DigestUtils.sha256Hex(request.getNewPassword().trim());
		repository.save(new UsuarioEntity(usuario.getLegajoId(), newHashed, usuario.getLastLog(),
				usuario.getBuild(), usuario.getUsuarioId(), usuario.getFacultadId()));
	}

}
