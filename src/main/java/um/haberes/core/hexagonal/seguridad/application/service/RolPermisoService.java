package um.haberes.core.hexagonal.seguridad.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import um.haberes.core.hexagonal.seguridad.application.exception.SeguridadException;
import um.haberes.core.hexagonal.seguridad.application.exception.SeguridadNotFoundException;
import um.haberes.core.hexagonal.seguridad.domain.model.Rol;
import um.haberes.core.hexagonal.seguridad.domain.model.RolPermiso;
import um.haberes.core.hexagonal.seguridad.domain.model.SeguridadConstantes;
import um.haberes.core.hexagonal.seguridad.domain.ports.out.PermisoRepository;
import um.haberes.core.hexagonal.seguridad.domain.ports.out.RolPermisoRepository;
import um.haberes.core.hexagonal.seguridad.domain.ports.out.RolRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RolPermisoService {

    private final RolPermisoRepository repository;
    private final RolRepository rolRepository;
    private final PermisoRepository permisoRepository;
    private final PermisoEfectivoService permisoEfectivoService;

    public List<RolPermiso> listarPorRol(Long rolId) {
        rolRepository.findById(rolId)
                .orElseThrow(() -> new SeguridadNotFoundException("No existe el rol " + rolId));
        return repository.findAllByRolId(rolId);
    }

    @Transactional
    public RolPermiso asignar(Long rolId, Long permisoId) {
        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new SeguridadNotFoundException("No existe el rol " + rolId));
        if (SeguridadConstantes.ROL_ADMIN.equalsIgnoreCase(rol.getNombre())) {
            throw new SeguridadException(
                    "El rol " + SeguridadConstantes.ROL_ADMIN + " se reconcilia desde roles.yaml y no se edita");
        }
        permisoRepository.findById(permisoId)
                .orElseThrow(() -> new SeguridadNotFoundException("No existe el permiso " + permisoId));
        return repository.findByRolIdAndPermisoId(rolId, permisoId)
                .orElseGet(() -> {
                    RolPermiso guardado = repository.save(RolPermiso.builder()
                            .rolId(rolId)
                            .permisoId(permisoId)
                            .build());
                    permisoEfectivoService.invalidar();
                    return guardado;
                });
    }

    @Transactional
    public void quitar(Long rolId, Long permisoId) {
        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new SeguridadNotFoundException("No existe el rol " + rolId));
        if (SeguridadConstantes.ROL_ADMIN.equalsIgnoreCase(rol.getNombre())) {
            throw new SeguridadException(
                    "El rol " + SeguridadConstantes.ROL_ADMIN + " se reconcilia desde roles.yaml y no se edita");
        }
        repository.findByRolIdAndPermisoId(rolId, permisoId).ifPresent(asignacion -> {
            repository.deleteById(asignacion.getRolPermisoId());
            permisoEfectivoService.invalidar();
        });
    }
}
