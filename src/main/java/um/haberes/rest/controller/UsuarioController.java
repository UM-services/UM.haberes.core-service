/**
 *
 */
package um.haberes.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import um.haberes.rest.exception.UsuarioException;
import um.haberes.rest.kotlin.model.Usuario;
import um.haberes.rest.service.UsuarioService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService service;

    @Autowired
    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping("/{legajoId}")
    public ResponseEntity<Usuario> findByLegajoId(@PathVariable Long legajoId) {
        try {
            return new ResponseEntity<>(service.findByLegajoId(legajoId), HttpStatus.OK);
        } catch (UsuarioException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/lastlog/{legajoId}")
    public ResponseEntity<Usuario> updateLastLog(@PathVariable Long legajoId) {
        try {
            return new ResponseEntity<>(service.updateLastLog(legajoId), HttpStatus.OK);
        } catch (UsuarioException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/isuservalid")
    public ResponseEntity<Boolean> isUserValid(@RequestBody Usuario usuario) {
        return new ResponseEntity<>(service.isUserValid(usuario), HttpStatus.OK);
    }

    @PutMapping("/setpassword")
    public ResponseEntity<Void> setPassword(@RequestBody Usuario usuario) {
        service.setPassword(usuario);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
