/**
 *
 */
package um.haberes.core.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import um.haberes.core.exception.UsuarioException;
import um.haberes.core.model.UsuarioEntity;
import um.haberes.core.model.dto.CambiarClaveRequest;
import um.haberes.core.model.dto.UsuarioResumenResponse;
import um.haberes.core.service.UsuarioService;

import java.util.List;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    /**
     * Listado para la pantalla de usuarios del modulo Administrador. No expone el
     * hash de la clave (ver {@link UsuarioResumenResponse}).
     */
    @GetMapping("/")
    public ResponseEntity<List<UsuarioResumenResponse>> findAll() {
        List<UsuarioResumenResponse> usuarios = service.findAll().stream()
                .map(usuario -> UsuarioResumenResponse.builder()
                        .legajoId(usuario.getLegajoId())
                        .usuarioId(usuario.getUsuarioId())
                        .facultadId(usuario.getFacultadId())
                        .administrador(usuario.getAdministrador())
                        .build())
                .toList();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @GetMapping("/{legajoId}")
    public ResponseEntity<UsuarioEntity> findByLegajoId(@PathVariable Long legajoId) {
        try {
            return new ResponseEntity<>(service.findByLegajoId(legajoId), HttpStatus.OK);
        } catch (UsuarioException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/lastlog/{legajoId}/{build}")
    public ResponseEntity<UsuarioEntity> updateLastLog(@PathVariable Long legajoId, @PathVariable Long build) {
        try {
            return new ResponseEntity<>(service.updateLastLog(legajoId, build), HttpStatus.OK);
        } catch (UsuarioException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/isuservalid")
    public ResponseEntity<Boolean> isUserValid(@RequestBody UsuarioEntity usuario) {
        return new ResponseEntity<>(service.isUserValid(usuario), HttpStatus.OK);
    }

    @PutMapping("/setpassword")
    public ResponseEntity<Void> setPassword(@RequestBody UsuarioEntity usuario) {
        service.setPassword(usuario);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{legajoId}/administrador/{administrador}")
    public ResponseEntity<UsuarioResumenResponse> setAdministrador(@PathVariable Long legajoId,
            @PathVariable Integer administrador) {
        try {
            UsuarioEntity usuario = service.setAdministrador(legajoId, administrador);
            return new ResponseEntity<>(UsuarioResumenResponse.builder()
                    .legajoId(usuario.getLegajoId())
                    .usuarioId(usuario.getUsuarioId())
                    .facultadId(usuario.getFacultadId())
                    .administrador(usuario.getAdministrador())
                    .build(), HttpStatus.OK);
        } catch (UsuarioException | IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Cambio de clave con verificacion server-side de la anterior. Devuelve el
     * mensaje de negocio en {@code detail} (ProblemDetail) con status 400,
     * patron del resto de endpoints de este controller.
     */
    @PutMapping("/cambiarclave")
    public ResponseEntity<Void> cambiarClave(@RequestBody CambiarClaveRequest request) {
        try {
            service.cambiarClave(request);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
