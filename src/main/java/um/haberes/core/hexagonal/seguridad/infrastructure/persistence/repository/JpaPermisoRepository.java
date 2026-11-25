package um.haberes.core.hexagonal.seguridad.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.hexagonal.seguridad.infrastructure.persistence.entity.PermisoEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaPermisoRepository extends JpaRepository<PermisoEntity, Long> {

    List<PermisoEntity> findAllByOrderByModuloAscClaveAsc();

    List<PermisoEntity> findAllByAplicacionOrderByModuloAscClaveAsc(String aplicacion);

    List<PermisoEntity> findAllByAplicacionAndActivoOrderByModuloAscClaveAsc(String aplicacion, Byte activo);

    Optional<PermisoEntity> findByAplicacionAndClave(String aplicacion, String clave);
}
