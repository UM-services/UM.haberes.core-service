package um.haberes.core.hexagonal.seguridad.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.hexagonal.seguridad.infrastructure.persistence.entity.UsuarioRolEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaUsuarioRolRepository extends JpaRepository<UsuarioRolEntity, Long> {

    List<UsuarioRolEntity> findAllByLegajoId(Long legajoId);

    List<UsuarioRolEntity> findAllByRolId(Long rolId);

    Optional<UsuarioRolEntity> findByLegajoIdAndRolId(Long legajoId, Long rolId);

    boolean existsByLegajoIdAndRolId(Long legajoId, Long rolId);

    void deleteByLegajoIdAndRolId(Long legajoId, Long rolId);
}
