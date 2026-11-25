package um.haberes.core.hexagonal.seguridad.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import um.haberes.core.hexagonal.seguridad.application.exception.SeguridadException;
import um.haberes.core.hexagonal.seguridad.application.exception.SeguridadNotFoundException;
import um.haberes.core.hexagonal.seguridad.domain.model.Permiso;
import um.haberes.core.hexagonal.seguridad.domain.ports.out.PermisoRepository;

import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class PermisoService {

    private static final Pattern CLAVE = Pattern.compile("^[a-zA-Z0-9_]+(\\.[a-zA-Z0-9_]+)+$");
    private static final Pattern APLICACION = Pattern.compile("^[A-Z0-9_]+$");
    private static final String APLICACION_DEFAULT = "HABERES";

    private final PermisoRepository repository;
    private final PermisoEfectivoService permisoEfectivoService;

    public List<Permiso> listar(String aplicacion, Boolean soloActivos) {
        String app = normalizarAplicacion(aplicacion);
        if (Boolean.TRUE.equals(soloActivos)) {
            return repository.findAllByAplicacionAndActivo(app, (byte) 1);
        }
        return repository.findAllByAplicacion(app);
    }

    public Permiso obtener(Long permisoId) {
        return repository.findById(permisoId)
                .orElseThrow(() -> new SeguridadNotFoundException("No existe el permiso " + permisoId));
    }

    @Transactional
    public Permiso crear(Permiso permiso) {
        Permiso normalizado = normalizar(permiso, true);
        repository.findByAplicacionAndClave(normalizado.getAplicacion(), normalizado.getClave())
                .ifPresent(existente -> {
                    throw new SeguridadException("Ya existe el permiso " + normalizado.getClave());
                });
        Permiso guardado = repository.save(normalizado);
        permisoEfectivoService.invalidar();
        return guardado;
    }

    @Transactional
    public Permiso actualizar(Long permisoId, Permiso permiso) {
        Permiso actual = obtener(permisoId);
        Permiso normalizado = normalizar(permiso, false);
        normalizado.setPermisoId(actual.getPermisoId());
        normalizado.setAplicacion(actual.getAplicacion());
        normalizado.setClave(actual.getClave());
        if (normalizado.getActivo() == null) {
            normalizado.setActivo(actual.getActivo());
        }
        Permiso guardado = repository.save(normalizado);
        permisoEfectivoService.invalidar();
        return guardado;
    }

    /**
     * Baja lógica: el permiso queda con activo = 0. No se borra físicamente para
     * no romper las asignaciones existentes (rol_permiso / usuario_permiso).
     */
    @Transactional
    public void retirar(Long permisoId) {
        Permiso actual = obtener(permisoId);
        actual.setActivo((byte) 0);
        repository.save(actual);
        permisoEfectivoService.invalidar();
    }

    private Permiso normalizar(Permiso permiso, boolean crear) {
        if (permiso == null) {
            throw new SeguridadException("El permiso es requerido");
        }
        String aplicacion = normalizarAplicacion(permiso.getAplicacion());
        String clave = permiso.getClave() == null ? "" : permiso.getClave().trim();
        if (clave.isEmpty() || !CLAVE.matcher(clave).matches()) {
            throw new SeguridadException(
                    "La clave debe tener el formato modulo.accion (sólo letras, números y guiones bajos): " + clave);
        }
        String modulo = permiso.getModulo() == null || permiso.getModulo().isBlank()
                ? clave.substring(0, clave.indexOf('.'))
                : permiso.getModulo().trim().toLowerCase(Locale.ROOT);
        String descripcion = permiso.getDescripcion() == null ? "" : permiso.getDescripcion().trim();
        if (descripcion.isEmpty()) {
            throw new SeguridadException("La descripción es obligatoria");
        }
        Byte activo = permiso.getActivo() == null ? (byte) 1 : permiso.getActivo();
        if (activo != 0 && activo != 1) {
            throw new SeguridadException("El valor de activo debe ser 0 o 1");
        }
        return Permiso.builder()
                .permisoId(crear ? null : permiso.getPermisoId())
                .clave(clave)
                .descripcion(descripcion)
                .modulo(modulo)
                .aplicacion(aplicacion)
                .activo(activo)
                .build();
    }

    private String normalizarAplicacion(String aplicacion) {
        if (aplicacion == null || aplicacion.isBlank()) {
            return APLICACION_DEFAULT;
        }
        String app = aplicacion.trim().toUpperCase(Locale.ROOT);
        if (!APLICACION.matcher(app).matches()) {
            throw new SeguridadException("Aplicación inválida: " + aplicacion);
        }
        return app;
    }
}
