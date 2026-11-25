package um.haberes.core.hexagonal.seguridad.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import um.haberes.core.hexagonal.seguridad.application.exception.ManifestException;
import um.haberes.core.hexagonal.seguridad.domain.model.Permiso;
import um.haberes.core.hexagonal.seguridad.domain.model.PermisoSemilla;
import um.haberes.core.hexagonal.seguridad.domain.model.Rol;
import um.haberes.core.hexagonal.seguridad.domain.model.RolPermiso;
import um.haberes.core.hexagonal.seguridad.domain.model.RolSemilla;
import um.haberes.core.hexagonal.seguridad.domain.model.SeguridadConstantes;
import um.haberes.core.hexagonal.seguridad.domain.model.SyncResultado;
import um.haberes.core.hexagonal.seguridad.domain.ports.out.PermisoRepository;
import um.haberes.core.hexagonal.seguridad.domain.ports.out.RolPermisoRepository;
import um.haberes.core.hexagonal.seguridad.domain.ports.out.RolRepository;
import um.haberes.core.hexagonal.seguridad.infrastructure.config.SeguridadProperties;
import um.haberes.core.hexagonal.seguridad.infrastructure.manifest.SeguridadManifestLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeguridadSyncService {

    private final SeguridadManifestLoader loader;
    private final PermisoRepository permisoRepository;
    private final RolRepository rolRepository;
    private final RolPermisoRepository rolPermisoRepository;
    private final SeguridadProperties properties;
    private final PermisoEfectivoService permisoEfectivoService;

    @Transactional
    public SyncResultado syncPermisos() {
        return syncPermisos(properties.isManifestDryRun());
    }

    @Transactional
    public SyncResultado syncPermisos(boolean dryRun) {
        List<PermisoSemilla> semillas = loader.loadPermisos();
        SyncResultado resultado = new SyncResultado();

        Map<String, Permiso> existentes = new HashMap<>();
        for (Permiso permiso : permisoRepository.findAll()) {
            existentes.put(etiqueta(permiso.getAplicacion(), permiso.getClave()), permiso);
        }

        Set<String> declaradas = new HashSet<>();
        for (PermisoSemilla semilla : semillas) {
            String etiqueta = etiqueta(semilla.getAplicacion(), semilla.getClave());
            declaradas.add(etiqueta);
            Permiso actual = existentes.get(etiqueta);
            if (actual == null) {
                resultado.getCreados().add(etiqueta);
                if (!dryRun) {
                    permisoRepository.save(Permiso.builder()
                            .aplicacion(semilla.getAplicacion())
                            .clave(semilla.getClave())
                            .modulo(semilla.getModulo())
                            .descripcion(semilla.getDescripcion())
                            .activo(semilla.getActivo())
                            .build());
                }
                continue;
            }
            boolean cambio = !Objects.equals(actual.getDescripcion(), semilla.getDescripcion())
                    || !Objects.equals(actual.getModulo(), semilla.getModulo())
                    || !Objects.equals(actual.getActivo(), semilla.getActivo());
            if (cambio) {
                resultado.getActualizados().add(etiqueta);
                if (!dryRun) {
                    actual.setDescripcion(semilla.getDescripcion());
                    actual.setModulo(semilla.getModulo());
                    actual.setActivo(semilla.getActivo());
                    permisoRepository.save(actual);
                }
            }
        }

        Set<String> aplicaciones = new HashSet<>();
        semillas.forEach(semilla -> aplicaciones.add(semilla.getAplicacion()));
        for (Permiso permiso : existentes.values()) {
            if (!aplicaciones.contains(permiso.getAplicacion())) {
                continue;
            }
            if (!declaradas.contains(etiqueta(permiso.getAplicacion(), permiso.getClave()))
                    && permiso.getActivo() != null && permiso.getActivo() == 1) {
                resultado.getRetirados().add(etiqueta(permiso.getAplicacion(), permiso.getClave()));
                if (!dryRun) {
                    permiso.setActivo((byte) 0);
                    permisoRepository.save(permiso);
                }
            }
        }

        log.info("Manifiesto de permisos {}: {} creados, {} actualizados, {} retirados",
                dryRun ? "(dry-run)" : "aplicado",
                resultado.getCreados().size(), resultado.getActualizados().size(),
                resultado.getRetirados().size());
        return resultado;
    }

    @Transactional
    public SyncResultado syncRoles() {
        return syncRoles(properties.isManifestDryRun());
    }

    @Transactional
    public SyncResultado syncRoles(boolean dryRun) {
        List<RolSemilla> semillas = loader.loadRoles();
        SyncResultado resultado = new SyncResultado();

        Map<String, Map<String, Permiso>> catalogos = new HashMap<>();
        for (RolSemilla semilla : semillas) {
            catalogos.computeIfAbsent(semilla.getAplicacion(), app -> {
                Map<String, Permiso> mapa = new LinkedHashMap<>();
                for (Permiso permiso : permisoRepository.findAllByAplicacionAndActivo(app, (byte) 1)) {
                    mapa.put(permiso.getClave(), permiso);
                }
                return mapa;
            });
        }

        for (RolSemilla semilla : semillas) {
            Map<String, Permiso> catalogo = catalogos.get(semilla.getAplicacion());
            Set<Long> declaradas = resolverPermisos(semilla, catalogo, resultado);
            if (SeguridadConstantes.ROL_ADMIN.equalsIgnoreCase(semilla.getNombre()) && declaradas.isEmpty()) {
                throw new ManifestException(
                        "El rol " + SeguridadConstantes.ROL_ADMIN + " quedó sin permisos: se rechaza el sync "
                                + "para no dejar el sistema sin administradores");
            }

            Rol actual = rolRepository
                    .findByAplicacionAndNombre(semilla.getAplicacion(), semilla.getNombre())
                    .orElse(null);

            if (actual == null) {
                resultado.getCreados().add(etiqueta(semilla.getAplicacion(), semilla.getNombre()));
                if (!dryRun) {
                    Rol creado = rolRepository.save(Rol.builder()
                            .nombre(semilla.getNombre())
                            .descripcion(semilla.getDescripcion())
                            .aplicacion(semilla.getAplicacion())
                            .activo(semilla.getActivo())
                            .build());
                    for (Long permisoId : declaradas) {
                        rolPermisoRepository.save(RolPermiso.builder()
                                .rolId(creado.getRolId())
                                .permisoId(permisoId)
                                .build());
                        resultado.getAsignacionesAgregadas().add(
                                semilla.getNombre() + " -> " + clavePorId(catalogo, permisoId));
                    }
                }
                continue;
            }

            boolean cambio = !Objects.equals(actual.getDescripcion(), semilla.getDescripcion())
                    || !Objects.equals(actual.getActivo(), semilla.getActivo());
            if (cambio) {
                resultado.getActualizados().add(etiqueta(semilla.getAplicacion(), semilla.getNombre()));
                if (!dryRun) {
                    actual.setDescripcion(semilla.getDescripcion());
                    actual.setActivo(semilla.getActivo());
                    rolRepository.save(actual);
                }
            }

            Set<Long> actuales = new LinkedHashSet<>();
            for (RolPermiso asignacion : rolPermisoRepository.findAllByRolId(actual.getRolId())) {
                actuales.add(asignacion.getPermisoId());
            }
            for (Long permisoId : declaradas) {
                if (actuales.contains(permisoId)) {
                    continue;
                }
                resultado.getAsignacionesAgregadas().add(
                        semilla.getNombre() + " -> " + clavePorId(catalogo, permisoId));
                if (!dryRun) {
                    rolPermisoRepository.save(RolPermiso.builder()
                            .rolId(actual.getRolId())
                            .permisoId(permisoId)
                            .build());
                }
            }
            for (Long permisoId : actuales) {
                if (declaradas.contains(permisoId)) {
                    continue;
                }
                resultado.getAsignacionesQuitadas().add(
                        semilla.getNombre() + " -> " + clavePorId(catalogo, permisoId));
                if (!dryRun) {
                    rolPermisoRepository.deleteByRolIdAndPermisoId(actual.getRolId(), permisoId);
                }
            }
        }

        log.info("Manifiesto de roles {}: {} creados, {} actualizados, {} asignaciones agregadas, {} quitadas",
                dryRun ? "(dry-run)" : "aplicado",
                resultado.getCreados().size(), resultado.getActualizados().size(),
                resultado.getAsignacionesAgregadas().size(), resultado.getAsignacionesQuitadas().size());
        if (!dryRun) {
            permisoEfectivoService.invalidar();
        }
        return resultado;
    }

    @Transactional
    public SyncResultado syncAll() {
        return syncAll(properties.isManifestDryRun());
    }

    @Transactional
    public SyncResultado syncAll(boolean dryRun) {
        SyncResultado permisos = syncPermisos(dryRun);
        SyncResultado roles = syncRoles(dryRun);
        return SyncResultado.builder()
                .creados(concat(permisos.getCreados(), roles.getCreados()))
                .actualizados(concat(permisos.getActualizados(), roles.getActualizados()))
                .retirados(permisos.getRetirados())
                .asignacionesAgregadas(roles.getAsignacionesAgregadas())
                .asignacionesQuitadas(roles.getAsignacionesQuitadas())
                .warnings(concat(permisos.getWarnings(), roles.getWarnings()))
                .build();
    }

    private Set<Long> resolverPermisos(RolSemilla semilla, Map<String, Permiso> catalogo, SyncResultado resultado) {
        Set<Long> ids = new LinkedHashSet<>();
        if (semilla.getPermisos() == null) {
            return ids;
        }
        for (String patron : semilla.getPermisos()) {
            if (patron == null || patron.isBlank()) {
                continue;
            }
            if ("*".equals(patron)) {
                catalogo.values().forEach(permiso -> ids.add(permiso.getPermisoId()));
                if (catalogo.isEmpty()) {
                    resultado.getWarnings().add(semilla.getNombre() + ": '*' sin claves en el catálogo");
                }
                continue;
            }
            if (patron.endsWith(".*")) {
                String prefijo = patron.substring(0, patron.length() - 1);
                long antes = ids.size();
                catalogo.forEach((clave, permiso) -> {
                    if (clave.startsWith(prefijo)) {
                        ids.add(permiso.getPermisoId());
                    }
                });
                if (ids.size() == antes) {
                    resultado.getWarnings().add(semilla.getNombre() + ": '" + patron + "' sin coincidencias");
                }
                continue;
            }
            Permiso permiso = catalogo.get(patron);
            if (permiso == null) {
                throw new ManifestException("El rol " + semilla.getNombre()
                        + " declara una clave inexistente: " + patron);
            }
            ids.add(permiso.getPermisoId());
        }
        return ids;
    }

    private String clavePorId(Map<String, Permiso> catalogo, Long permisoId) {
        return catalogo.values().stream()
                .filter(permiso -> Objects.equals(permiso.getPermisoId(), permisoId))
                .map(Permiso::getClave)
                .findFirst()
                .orElse("permiso#" + permisoId);
    }

    private List<String> concat(List<String> first, List<String> second) {
        List<String> result = new ArrayList<>(first);
        result.addAll(second);
        return result;
    }

    private String etiqueta(String aplicacion, String valor) {
        return aplicacion + ":" + valor;
    }
}
