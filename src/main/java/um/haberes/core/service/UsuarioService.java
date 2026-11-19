/**
 * 
 */
package um.haberes.core.service;

import java.util.List;
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

	private static final Byte ADMINISTRADOR = (byte) 1;

	private final JpaUsuarioRepository repository;

	@Autowired
	public UsuarioService(JpaUsuarioRepository repository) {
		this.repository = repository;
	}

	public List<UsuarioEntity> findAll() {
		return repository.findAllByOrderByLegajoIdAsc();
	}

	public UsuarioEntity findByLegajoId(Long legajoId) {
		return repository.findByLegajoId(legajoId).orElseThrow(() -> new UsuarioException(legajoId));
	}

	public UsuarioEntity updateLastLog(Long legajoId, Long build) {
		return repository.findByLegajoId(legajoId).map(usuario -> repository.save(usuario.toBuilder()
				.lastLog(Tool.hourAbsoluteArgentina())
				.build(build)
				.build())).orElseThrow(() -> new UsuarioException(legajoId));
	}

	public Boolean isUserValid(UsuarioEntity usuario) {
		Optional<UsuarioEntity> user_opt = repository.findByLegajoIdAndPassword(usuario.getLegajoId(),
				DigestUtils.sha256Hex(usuario.getPassword()));
		return user_opt.isPresent();
	}

	public void setPassword(UsuarioEntity newUsuario) {
		UsuarioEntity usuario = repository.findByLegajoId(newUsuario.getLegajoId()).get();
		repository.save(usuario.toBuilder()
				.password(DigestUtils.sha256Hex(newUsuario.getPassword()))
				.build(newUsuario.getBuild())
				.build());
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
		repository.save(usuario.toBuilder().password(newHashed).build());
	}

	/**
	 * Habilita o revoca el flag administrador de un usuario.
	 *
	 * Reglas (PLANIFICACION_CAMPO_ADMINISTRADOR_USUARIO_HABERES.md, D1/D5/D6):
	 * solo usuarios sin facultad asignada pueden ser administradores y el sistema
	 * nunca puede quedar sin al menos un administrador.
	 *
	 * @param legajoId      usuario destino
	 * @param administrador 1 habilita, 0 revoca
	 * @throws IllegalArgumentException con el mensaje de negocio
	 * @throws UsuarioException         si el usuario no existe
	 */
	public UsuarioEntity setAdministrador(Long legajoId, Integer administrador) {
		if (administrador == null || (administrador != 0 && administrador != 1)) {
			throw new IllegalArgumentException("ERROR: Valor de administrador invalido");
		}

		UsuarioEntity usuario = repository.findByLegajoId(legajoId).orElseThrow(() -> new UsuarioException(legajoId));
		Byte nuevoValor = administrador.byteValue();

		if (ADMINISTRADOR.equals(nuevoValor) && usuario.getFacultadId() != null) {
			throw new IllegalArgumentException("ERROR: Usuario con facultad asignada NO puede ser administrador");
		}

		boolean revoca = Byte.valueOf((byte) 0).equals(nuevoValor)
				&& ADMINISTRADOR.equals(usuario.getAdministrador());
		if (revoca && repository.countByAdministrador(ADMINISTRADOR) <= 1) {
			throw new IllegalArgumentException("ERROR: Debe quedar al menos un administrador");
		}

		return repository.save(usuario.toBuilder().administrador(nuevoValor).build());
	}

}
