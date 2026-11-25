package um.haberes.core.hexagonal.seguridad.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.hexagonal.seguridad.infrastructure.persistence.entity.UsuarioPermisoEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaUsuarioPermisoRepository extends JpaRepository<UsuarioPermisoEntity, Long> {

    List<UsuarioPermisoEntity> findAllByLegajoId(Long legajoId);

    List<UsuarioPermisoEntity> findAllByPermisoId(Long permisoId);

    Optional<UsuarioPermisoEntity> findByLegajoIdAndPermisoId(Long legajoId, Long permisoId);

    void deleteByLegajoIdAndPermisoId(Long legajoId, Long permisoId);
}
