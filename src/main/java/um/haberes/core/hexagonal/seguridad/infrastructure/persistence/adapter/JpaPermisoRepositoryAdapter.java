package um.haberes.core.hexagonal.seguridad.infrastructure.persistence.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import um.haberes.core.hexagonal.seguridad.domain.model.Permiso;
import um.haberes.core.hexagonal.seguridad.domain.ports.out.PermisoRepository;
import um.haberes.core.hexagonal.seguridad.infrastructure.persistence.entity.PermisoEntity;
import um.haberes.core.hexagonal.seguridad.infrastructure.persistence.repository.JpaPermisoRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaPermisoRepositoryAdapter implements PermisoRepository {

    private final JpaPermisoRepository repository;

    @Override
    public List<Permiso> findAll() {
        return repository.findAllByOrderByModuloAscClaveAsc().stream().map(this::toDomain).toList();
    }

    @Override
    public List<Permiso> findAllByAplicacion(String aplicacion) {
        return repository.findAllByAplicacionOrderByModuloAscClaveAsc(aplicacion).stream().map(this::toDomain).toList();
    }

    @Override
    public List<Permiso> findAllByAplicacionAndActivo(String aplicacion, Byte activo) {
        return repository.findAllByAplicacionAndActivoOrderByModuloAscClaveAsc(aplicacion, activo).stream()
                .map(this::toDomain).toList();
    }

    @Override
    public Optional<Permiso> findById(Long permisoId) {
        return repository.findById(permisoId).map(this::toDomain);
    }

    @Override
    public Optional<Permiso> findByAplicacionAndClave(String aplicacion, String clave) {
        return repository.findByAplicacionAndClave(aplicacion, clave).map(this::toDomain);
    }

    @Override
    public Permiso save(Permiso permiso) {
        return toDomain(repository.save(toEntity(permiso)));
    }

    @Override
    public void deleteById(Long permisoId) {
        repository.deleteById(permisoId);
    }

    private Permiso toDomain(PermisoEntity entity) {
        return Permiso.builder()
                .permisoId(entity.getPermisoId())
                .clave(entity.getClave())
                .descripcion(entity.getDescripcion())
                .modulo(entity.getModulo())
                .aplicacion(entity.getAplicacion())
                .activo(entity.getActivo())
                .build();
    }

    private PermisoEntity toEntity(Permiso permiso) {
        return PermisoEntity.builder()
                .permisoId(permiso.getPermisoId())
                .clave(permiso.getClave())
                .descripcion(permiso.getDescripcion())
                .modulo(permiso.getModulo())
                .aplicacion(permiso.getAplicacion())
                .activo(permiso.getActivo())
                .build();
    }
}
