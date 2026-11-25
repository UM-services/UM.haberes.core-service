package um.haberes.core.hexagonal.seguridad.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.hexagonal.seguridad.infrastructure.persistence.entity.RolEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaRolRepository extends JpaRepository<RolEntity, Long> {

    List<RolEntity> findAllByOrderByAplicacionAscNombreAsc();

    List<RolEntity> findAllByAplicacionOrderByNombreAsc(String aplicacion);

    Optional<RolEntity> findByAplicacionAndNombre(String aplicacion, String nombre);

    boolean existsByAplicacionAndNombre(String aplicacion, String nombre);
}
