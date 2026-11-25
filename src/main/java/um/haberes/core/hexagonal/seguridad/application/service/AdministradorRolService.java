package um.haberes.core.hexagonal.seguridad.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import um.haberes.core.hexagonal.seguridad.domain.model.Rol;
import um.haberes.core.hexagonal.seguridad.domain.model.SeguridadConstantes;
import um.haberes.core.hexagonal.seguridad.domain.model.UsuarioRol;
import um.haberes.core.hexagonal.seguridad.domain.ports.out.RolRepository;
import um.haberes.core.hexagonal.seguridad.domain.ports.out.UsuarioRolRepository;

import java.util.Optional;

/**
 * Mantiene la coherencia entre el flag `usuario.administrador` y el rol
 * ADMIN_HABERES. El flag es el interruptor; el rol es el que aporta las claves.
 * La lectura de permisos efectivos además refuerza la membresía (ver
 * {@link PermisoEfectivoService}), así una restauración parcial no deja al
 * administrador sin acceso.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AdministradorRolService {

    private final RolRepository rolRepository;
    private final UsuarioRolRepository usuarioRolRepository;
    private final PermisoEfectivoService permisoEfectivoService;

    @Transactional
    public void sincronizar(Long legajoId, Integer administrador) {
        Optional<Rol> rolAdmin = rolRepository.findByAplicacionAndNombre(
                SeguridadConstantes.APLICACION, SeguridadConstantes.ROL_ADMIN);
        if (rolAdmin.isEmpty()) {
            log.warn("No existe el rol {}: no se pudo sincronizar el administrador {}",
                    SeguridadConstantes.ROL_ADMIN, legajoId);
            return;
        }
        Long rolId = rolAdmin.get().getRolId();
        if (administrador != null && administrador == 1) {
            if (!usuarioRolRepository.existsByLegajoIdAndRolId(legajoId, rolId)) {
                usuarioRolRepository.save(UsuarioRol.builder()
                        .legajoId(legajoId)
                        .rolId(rolId)
                        .build());
            }
        } else {
            usuarioRolRepository.findByLegajoIdAndRolId(legajoId, rolId)
                    .ifPresent(asignacion -> usuarioRolRepository.deleteById(asignacion.getUsuarioRolId()));
        }
        permisoEfectivoService.invalidar();
    }
}
