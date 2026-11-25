package um.haberes.core.hexagonal.seguridad.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import um.haberes.core.hexagonal.seguridad.application.exception.SeguridadException;
import um.haberes.core.hexagonal.seguridad.application.exception.SeguridadNotFoundException;
import um.haberes.core.hexagonal.seguridad.domain.model.Rol;
import um.haberes.core.hexagonal.seguridad.domain.model.RolPermiso;
import um.haberes.core.hexagonal.seguridad.domain.model.SeguridadConstantes;
import um.haberes.core.hexagonal.seguridad.domain.model.UsuarioRol;
import um.haberes.core.hexagonal.seguridad.domain.ports.out.RolPermisoRepository;
import um.haberes.core.hexagonal.seguridad.domain.ports.out.RolRepository;
import um.haberes.core.hexagonal.seguridad.domain.ports.out.UsuarioRolRepository;

import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class RolService {

    private static final Pattern NOMBRE = Pattern.compile("^[A-Z0-9_]{2,80}$");

    private final RolRepository repository;
    private final RolPermisoRepository rolPermisoRepository;
    private final UsuarioRolRepository usuarioRolRepository;
    private final PermisoEfectivoService permisoEfectivoService;

    public List<Rol> listar(String aplicacion) {
        String app = aplicacion == null || aplicacion.isBlank()
                ? SeguridadConstantes.APLICACION : aplicacion.trim().toUpperCase(Locale.ROOT);
        return repository.findAllByAplicacion(app);
    }

    public Rol obtener(Long rolId) {
        return repository.findById(rolId)
                .orElseThrow(() -> new SeguridadNotFoundException("No existe el rol " + rolId));
    }

    @Transactional
    public Rol crear(Rol rol) {
        Rol normalizado = normalizar(rol);
        repository.findByAplicacionAndNombre(normalizado.getAplicacion(), normalizado.getNombre())
                .ifPresent(existente -> {
                    throw new SeguridadException("Ya existe el rol " + normalizado.getNombre());
                });
        return repository.save(normalizado);
    }

    @Transactional
    public Rol actualizar(Long rolId, Rol rol) {
        Rol actual = obtener(rolId);
        if (esRolAdmin(actual)) {
            throw new SeguridadException("El rol " + SeguridadConstantes.ROL_ADMIN + " es protegido y no se puede editar");
        }
        Rol normalizado = normalizar(rol);
        normalizado.setRolId(actual.getRolId());
        normalizado.setAplicacion(actual.getAplicacion());
        normalizado.setNombre(actual.getNombre());
        if (normalizado.getActivo() == null) {
            normalizado.setActivo(actual.getActivo());
        }
        Rol guardado = repository.save(normalizado);
        permisoEfectivoService.invalidar();
        return guardado;
    }

    @Transactional
    public void eliminar(Long rolId) {
        Rol actual = obtener(rolId);
        if (esRolAdmin(actual)) {
            throw new SeguridadException("El rol " + SeguridadConstantes.ROL_ADMIN + " es protegido y no se puede eliminar");
        }
        for (RolPermiso asignacion : rolPermisoRepository.findAllByRolId(rolId)) {
            rolPermisoRepository.deleteById(asignacion.getRolPermisoId());
        }
        for (UsuarioRol asignacion : usuarioRolRepository.findAllByRolId(rolId)) {
            usuarioRolRepository.deleteById(asignacion.getUsuarioRolId());
        }
        repository.deleteById(rolId);
        permisoEfectivoService.invalidar();
    }

    public boolean esRolAdmin(Rol rol) {
        return rol != null
                && SeguridadConstantes.ROL_ADMIN.equalsIgnoreCase(rol.getNombre())
                && SeguridadConstantes.APLICACION.equalsIgnoreCase(rol.getAplicacion());
    }

    private Rol normalizar(Rol rol) {
        if (rol == null) {
            throw new SeguridadException("El rol es requerido");
        }
        String nombre = rol.getNombre() == null ? "" : rol.getNombre().trim().toUpperCase(Locale.ROOT);
        if (!NOMBRE.matcher(nombre).matches()) {
            throw new SeguridadException(
                    "El nombre del rol debe tener 2-80 caracteres en mayúsculas, números o guión bajo");
        }
        String aplicacion = rol.getAplicacion() == null || rol.getAplicacion().isBlank()
                ? SeguridadConstantes.APLICACION : rol.getAplicacion().trim().toUpperCase(Locale.ROOT);
        Byte activo = rol.getActivo() == null ? (byte) 1 : rol.getActivo();
        if (activo != 0 && activo != 1) {
            throw new SeguridadException("El valor de activo debe ser 0 o 1");
        }
        return Rol.builder()
                .rolId(rol.getRolId())
                .nombre(nombre)
                .descripcion(rol.getDescripcion() == null ? null : rol.getDescripcion().trim())
                .aplicacion(aplicacion)
                .activo(activo)
                .build();
    }
}
