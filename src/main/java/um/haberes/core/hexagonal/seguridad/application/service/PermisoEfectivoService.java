package um.haberes.core.hexagonal.seguridad.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import um.haberes.core.hexagonal.seguridad.application.exception.SeguridadNotFoundException;
import um.haberes.core.hexagonal.seguridad.domain.model.Permiso;
import um.haberes.core.hexagonal.seguridad.domain.model.PermisoEfectivo;
import um.haberes.core.hexagonal.seguridad.domain.model.Rol;
import um.haberes.core.hexagonal.seguridad.domain.model.RolPermiso;
import um.haberes.core.hexagonal.seguridad.domain.model.SeguridadConstantes;
import um.haberes.core.hexagonal.seguridad.domain.model.UsuarioPermiso;
import um.haberes.core.hexagonal.seguridad.domain.model.UsuarioRol;
import um.haberes.core.hexagonal.seguridad.domain.ports.out.PermisoRepository;
import um.haberes.core.hexagonal.seguridad.domain.ports.out.RolPermisoRepository;
import um.haberes.core.hexagonal.seguridad.domain.ports.out.RolRepository;
import um.haberes.core.hexagonal.seguridad.domain.ports.out.UsuarioPermisoRepository;
import um.haberes.core.hexagonal.seguridad.domain.ports.out.UsuarioRolRepository;
import um.haberes.core.model.UsuarioEntity;
import um.haberes.core.repository.JpaUsuarioRepository;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

@Service
@RequiredArgsConstructor
public class PermisoEfectivoService {

    public static final String CACHE = "permisosEfectivos";

    private final JpaUsuarioRepository usuarioRepository;
    private final PermisoRepository permisoRepository;
    private final RolRepository rolRepository;
    private final RolPermisoRepository rolPermisoRepository;
    private final UsuarioRolRepository usuarioRolRepository;
    private final UsuarioPermisoRepository usuarioPermisoRepository;

    @Cacheable(cacheNames = CACHE, key = "#legajoId")
    public PermisoEfectivo obtener(Long legajoId) {
        if (legajoId == null) {
            throw new SeguridadNotFoundException("legajoId es requerido");
        }
        UsuarioEntity usuario = usuarioRepository.findByLegajoId(legajoId)
                .orElseThrow(() -> new SeguridadNotFoundException("No existe el usuario " + legajoId));

        boolean administrador = usuario.getAdministrador() != null && usuario.getAdministrador() == 1;

        Map<Long, Permiso> catalogo = new LinkedHashMap<>();
        for (Permiso permiso : permisoRepository.findAllByAplicacionAndActivo(
                SeguridadConstantes.APLICACION, (byte) 1)) {
            catalogo.put(permiso.getPermisoId(), permiso);
        }

        Map<Long, Rol> roles = new LinkedHashMap<>();
        for (Rol rol : rolRepository.findAllByAplicacion(SeguridadConstantes.APLICACION)) {
            roles.put(rol.getRolId(), rol);
        }

        Set<Long> permisoIds = new HashSet<>();

        // 1) Herencia de roles activos.
        for (UsuarioRol asignacion : usuarioRolRepository.findAllByLegajoId(legajoId)) {
            Rol rol = roles.get(asignacion.getRolId());
            if (rol == null || rol.getActivo() == null || rol.getActivo() != 1) {
                continue;
            }
            for (RolPermiso rolPermiso : rolPermisoRepository.findAllByRolId(rol.getRolId())) {
                permisoIds.add(rolPermiso.getPermisoId());
            }
        }

        // 2) Overrides individuales: 1 otorga, 0 revoca.
        for (UsuarioPermiso override : usuarioPermisoRepository.findAllByLegajoId(legajoId)) {
            if (override.getOtorgado() != null && override.getOtorgado() == 1) {
                permisoIds.add(override.getPermisoId());
            } else {
                permisoIds.remove(override.getPermisoId());
            }
        }

        // 3) Acceso total del administrador: se aplica al final para que ningún
        //    override pueda revocarle una clave.
        if (administrador) {
            Rol rolAdmin = roles.values().stream()
                    .filter(rol -> SeguridadConstantes.ROL_ADMIN.equalsIgnoreCase(rol.getNombre()))
                    .findFirst()
                    .orElse(null);
            Set<Long> delRolAdmin = new HashSet<>();
            if (rolAdmin != null) {
                for (RolPermiso rolPermiso : rolPermisoRepository.findAllByRolId(rolAdmin.getRolId())) {
                    delRolAdmin.add(rolPermiso.getPermisoId());
                }
            }
            // Si el rol no existe o quedó vacío, el administrador conserva acceso
            // total igualmente (defensa ante una restauración parcial).
            if (delRolAdmin.isEmpty()) {
                permisoIds.addAll(catalogo.keySet());
            } else {
                permisoIds.addAll(delRolAdmin);
            }
        }

        Set<String> claves = new TreeSet<>();
        for (Long permisoId : permisoIds) {
            Permiso permiso = catalogo.get(permisoId);
            if (permiso != null && permiso.getClave() != null) {
                claves.add(permiso.getClave());
            }
        }

        return PermisoEfectivo.builder()
                .legajoId(legajoId)
                .permisos(List.copyOf(claves))
                .administrador(administrador)
                .build();
    }

    public boolean tiene(Long legajoId, String clave) {
        return obtener(legajoId).getPermisos().contains(clave);
    }

    @CacheEvict(cacheNames = CACHE, allEntries = true)
    public void invalidar() {
        // La anotación limpia la caché; el cuerpo queda vacío a propósito.
    }
}
