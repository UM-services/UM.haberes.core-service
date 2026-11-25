package um.haberes.core.hexagonal.seguridad.domain.ports.out;

import um.haberes.core.hexagonal.seguridad.domain.model.Permiso;

import java.util.List;
import java.util.Optional;

public interface PermisoRepository {

    List<Permiso> findAll();

    List<Permiso> findAllByAplicacion(String aplicacion);

    List<Permiso> findAllByAplicacionAndActivo(String aplicacion, Byte activo);

    Optional<Permiso> findById(Long permisoId);

    Optional<Permiso> findByAplicacionAndClave(String aplicacion, String clave);

    Permiso save(Permiso permiso);

    void deleteById(Long permisoId);
}
