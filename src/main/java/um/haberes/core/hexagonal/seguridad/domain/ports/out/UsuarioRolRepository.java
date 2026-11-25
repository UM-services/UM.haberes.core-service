package um.haberes.core.hexagonal.seguridad.domain.ports.out;

import um.haberes.core.hexagonal.seguridad.domain.model.UsuarioRol;

import java.util.List;
import java.util.Optional;

public interface UsuarioRolRepository {

    List<UsuarioRol> findAllByLegajoId(Long legajoId);

    List<UsuarioRol> findAllByRolId(Long rolId);

    Optional<UsuarioRol> findByLegajoIdAndRolId(Long legajoId, Long rolId);

    boolean existsByLegajoIdAndRolId(Long legajoId, Long rolId);

    UsuarioRol save(UsuarioRol usuarioRol);

    void deleteById(Long usuarioRolId);

    void deleteByLegajoIdAndRolId(Long legajoId, Long rolId);
}
