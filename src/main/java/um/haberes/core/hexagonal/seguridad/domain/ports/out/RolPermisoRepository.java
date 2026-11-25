package um.haberes.core.hexagonal.seguridad.domain.ports.out;

import um.haberes.core.hexagonal.seguridad.domain.model.RolPermiso;

import java.util.List;
import java.util.Optional;

public interface RolPermisoRepository {

    List<RolPermiso> findAllByRolId(Long rolId);

    List<RolPermiso> findAllByPermisoId(Long permisoId);

    Optional<RolPermiso> findByRolIdAndPermisoId(Long rolId, Long permisoId);

    RolPermiso save(RolPermiso rolPermiso);

    void deleteById(Long rolPermisoId);

    void deleteByRolIdAndPermisoId(Long rolId, Long permisoId);
}
