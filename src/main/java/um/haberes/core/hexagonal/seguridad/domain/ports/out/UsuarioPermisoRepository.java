package um.haberes.core.hexagonal.seguridad.domain.ports.out;

import um.haberes.core.hexagonal.seguridad.domain.model.UsuarioPermiso;

import java.util.List;
import java.util.Optional;

public interface UsuarioPermisoRepository {

    List<UsuarioPermiso> findAllByLegajoId(Long legajoId);

    List<UsuarioPermiso> findAllByPermisoId(Long permisoId);

    Optional<UsuarioPermiso> findByLegajoIdAndPermisoId(Long legajoId, Long permisoId);

    UsuarioPermiso save(UsuarioPermiso usuarioPermiso);

    void deleteById(Long usuarioPermisoId);

    void deleteByLegajoIdAndPermisoId(Long legajoId, Long permisoId);
}
