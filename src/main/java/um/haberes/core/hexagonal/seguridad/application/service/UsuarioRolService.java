package um.haberes.core.hexagonal.seguridad.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import um.haberes.core.hexagonal.seguridad.application.exception.SeguridadException;
import um.haberes.core.hexagonal.seguridad.application.exception.SeguridadNotFoundException;
import um.haberes.core.hexagonal.seguridad.domain.model.Rol;
import um.haberes.core.hexagonal.seguridad.domain.model.SeguridadConstantes;
import um.haberes.core.hexagonal.seguridad.domain.model.UsuarioRol;
import um.haberes.core.hexagonal.seguridad.domain.ports.out.RolRepository;
import um.haberes.core.hexagonal.seguridad.domain.ports.out.UsuarioRolRepository;
import um.haberes.core.model.UsuarioEntity;
import um.haberes.core.repository.JpaUsuarioRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioRolService {

    private final UsuarioRolRepository repository;
    private final RolRepository rolRepository;
    private final JpaUsuarioRepository usuarioRepository;
    private final PermisoEfectivoService permisoEfectivoService;

    public List<UsuarioRol> listarPorLegajo(Long legajoId) {
        usuarioRepository.findByLegajoId(legajoId)
                .orElseThrow(() -> new SeguridadNotFoundException("No existe el usuario " + legajoId));
        return repository.findAllByLegajoId(legajoId);
    }

    @Transactional
    public UsuarioRol asignar(Long legajoId, Long rolId) {
        usuarioRepository.findByLegajoId(legajoId)
                .orElseThrow(() -> new SeguridadNotFoundException("No existe el usuario " + legajoId));
        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new SeguridadNotFoundException("No existe el rol " + rolId));
        if (esRolAdmin(rol)) {
            throw new SeguridadException(
                    "El rol " + SeguridadConstantes.ROL_ADMIN + " se asigna automáticamente con el flag administrador");
        }
        return repository.findByLegajoIdAndRolId(legajoId, rolId)
                .orElseGet(() -> {
                    UsuarioRol guardado = repository.save(UsuarioRol.builder()
                            .legajoId(legajoId)
                            .rolId(rolId)
                            .build());
                    permisoEfectivoService.invalidar();
                    return guardado;
                });
    }

    @Transactional
    public void quitar(Long legajoId, Long rolId) {
        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new SeguridadNotFoundException("No existe el rol " + rolId));
        if (esRolAdmin(rol)) {
            UsuarioEntity usuario = usuarioRepository.findByLegajoId(legajoId)
                    .orElseThrow(() -> new SeguridadNotFoundException("No existe el usuario " + legajoId));
            if (usuario.getAdministrador() != null && usuario.getAdministrador() == 1) {
                throw new SeguridadException(
                        "No se puede quitar " + SeguridadConstantes.ROL_ADMIN
                                + " a un usuario administrador; revocá primero el flag administrador");
            }
        }
        repository.findByLegajoIdAndRolId(legajoId, rolId).ifPresent(asignacion -> {
            repository.deleteById(asignacion.getUsuarioRolId());
            permisoEfectivoService.invalidar();
        });
    }

    private boolean esRolAdmin(Rol rol) {
        return rol != null
                && SeguridadConstantes.ROL_ADMIN.equalsIgnoreCase(rol.getNombre())
                && SeguridadConstantes.APLICACION.equalsIgnoreCase(rol.getAplicacion());
    }
}
