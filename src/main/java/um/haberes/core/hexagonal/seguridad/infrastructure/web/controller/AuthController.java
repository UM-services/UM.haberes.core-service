package um.haberes.core.hexagonal.seguridad.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import um.haberes.core.exception.UsuarioException;
import um.haberes.core.hexagonal.personas.persona.application.exception.PersonaException;
import um.haberes.core.hexagonal.personas.persona.application.service.PersonaService;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;
import um.haberes.core.hexagonal.seguridad.infrastructure.security.TokenService;
import um.haberes.core.hexagonal.seguridad.infrastructure.web.dto.AuthRequest;
import um.haberes.core.hexagonal.seguridad.infrastructure.web.dto.AuthResponse;
import um.haberes.core.hexagonal.seguridad.infrastructure.web.dto.PreLoginResponse;
import um.haberes.core.model.UsuarioEntity;
import um.haberes.core.service.UsuarioService;

@RestController
@RequestMapping("/api/haberes/core/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final long BUILD_DEFAULT = 1L;

    private final UsuarioService usuarioService;
    private final PersonaService personaService;
    private final TokenService tokenService;

    /**
     * Datos mínimos para la pantalla de login (nombre y flags). Endpoint público
     * excluido del filtro de token: no devuelve documento, CUIL, facultad,
     * permisos ni el hash de la clave.
     */
    @GetMapping("/legajo/{legajoId}")
    public ResponseEntity<PreLoginResponse> datosLegajo(@PathVariable Long legajoId) {
        Persona persona;
        try {
            persona = personaService.findByLegajoId(legajoId);
        } catch (PersonaException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Legajo NO encontrado");
        }
        UsuarioEntity usuario;
        try {
            usuario = usuarioService.findByLegajoId(legajoId);
        } catch (UsuarioException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario NO encontrado");
        }
        String apellidoNombre = (persona.getApellido() + ", " + persona.getNombre()).trim();
        boolean primerIngreso = usuario.getPassword() == null || usuario.getPassword().isBlank();
        return ResponseEntity.ok(PreLoginResponse.builder()
                .legajoId(legajoId)
                .apellidoNombre(apellidoNombre)
                .habilitado(usuario.getUsuarioId() != null)
                .primerIngreso(primerIngreso)
                .build());
    }

    /**
     * Login de Haberes: valida legajo + clave con la misma lógica que
     * `PUT /usuario/isuservalid`, actualiza last_log y emite el token firmado.
     * Endpoint público (el filtro de autenticación lo excluye).
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        if (request == null || request.getLegajoId() == null
                || request.getPassword() == null || request.getPassword().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "legajoId y password son obligatorios");
        }
        UsuarioEntity probe = UsuarioEntity.builder()
                .legajoId(request.getLegajoId())
                .password(request.getPassword())
                .build();
        if (!Boolean.TRUE.equals(usuarioService.isUserValid(probe))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario NO Autenticado");
        }

        UsuarioEntity usuario = usuarioService.findByLegajoId(request.getLegajoId());
        if (usuario.getUsuarioId() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario NO HABILITADO");
        }
        if (request.getBuild() != null) {
            usuarioService.updateLastLog(usuario.getLegajoId(), request.getBuild());
        } else {
            usuarioService.updateLastLog(usuario.getLegajoId(), BUILD_DEFAULT);
        }

        TokenService.Token token = tokenService.emitir(usuario.getLegajoId());
        boolean administrador = usuario.getAdministrador() != null && usuario.getAdministrador() == 1;
        return ResponseEntity.ok(AuthResponse.builder()
                .token(token.token())
                .expiresAt(token.expira())
                .legajoId(usuario.getLegajoId())
                .administrador(administrador)
                .build());
    }
}
