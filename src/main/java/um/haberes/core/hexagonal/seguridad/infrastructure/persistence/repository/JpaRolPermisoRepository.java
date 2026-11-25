package um.haberes.core.hexagonal.seguridad.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.hexagonal.seguridad.infrastructure.persistence.entity.RolPermisoEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaRolPermisoRepository extends JpaRepository<RolPermisoEntity, Long> {

    List<RolPermisoEntity> findAllByRolId(Long rolId);

    List<RolPermisoEntity> findAllByPermisoId(Long permisoId);

    Optional<RolPermisoEntity> findByRolIdAndPermisoId(Long rolId, Long permisoId);

    void deleteByRolIdAndPermisoId(Long rolId, Long permisoId);
}
