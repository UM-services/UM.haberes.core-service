package um.haberes.core.hexagonal.seguridad.domain.ports.out;

import um.haberes.core.hexagonal.seguridad.domain.model.Rol;

import java.util.List;
import java.util.Optional;

public interface RolRepository {

    List<Rol> findAll();

    List<Rol> findAllByAplicacion(String aplicacion);

    Optional<Rol> findById(Long rolId);

    Optional<Rol> findByAplicacionAndNombre(String aplicacion, String nombre);

    Rol save(Rol rol);

    void deleteById(Long rolId);
}
