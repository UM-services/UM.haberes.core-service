package um.haberes.core.hexagonal.seguridad.infrastructure.persistence.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import um.haberes.core.hexagonal.seguridad.domain.model.Rol;
import um.haberes.core.hexagonal.seguridad.domain.ports.out.RolRepository;
import um.haberes.core.hexagonal.seguridad.infrastructure.persistence.entity.RolEntity;
import um.haberes.core.hexagonal.seguridad.infrastructure.persistence.repository.JpaRolRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaRolRepositoryAdapter implements RolRepository {

    private final JpaRolRepository repository;

    @Override
    public List<Rol> findAll() {
        return repository.findAllByOrderByAplicacionAscNombreAsc().stream().map(this::toDomain).toList();
    }

    @Override
    public List<Rol> findAllByAplicacion(String aplicacion) {
        return repository.findAllByAplicacionOrderByNombreAsc(aplicacion).stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<Rol> findById(Long rolId) {
        return repository.findById(rolId).map(this::toDomain);
    }

    @Override
    public Optional<Rol> findByAplicacionAndNombre(String aplicacion, String nombre) {
        return repository.findByAplicacionAndNombre(aplicacion, nombre).map(this::toDomain);
    }

    @Override
    public Rol save(Rol rol) {
        return toDomain(repository.save(toEntity(rol)));
    }

    @Override
    public void deleteById(Long rolId) {
        repository.deleteById(rolId);
    }

    private Rol toDomain(RolEntity entity) {
        return Rol.builder()
                .rolId(entity.getRolId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .aplicacion(entity.getAplicacion())
                .activo(entity.getActivo())
                .build();
    }

    private RolEntity toEntity(Rol rol) {
        return RolEntity.builder()
                .rolId(rol.getRolId())
                .nombre(rol.getNombre())
                .descripcion(rol.getDescripcion())
                .aplicacion(rol.getAplicacion())
                .activo(rol.getActivo())
                .build();
    }
}
