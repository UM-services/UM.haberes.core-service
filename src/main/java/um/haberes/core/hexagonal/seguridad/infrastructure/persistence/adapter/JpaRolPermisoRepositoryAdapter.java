package um.haberes.core.hexagonal.seguridad.infrastructure.persistence.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import um.haberes.core.hexagonal.seguridad.domain.model.RolPermiso;
import um.haberes.core.hexagonal.seguridad.domain.ports.out.RolPermisoRepository;
import um.haberes.core.hexagonal.seguridad.infrastructure.persistence.entity.RolPermisoEntity;
import um.haberes.core.hexagonal.seguridad.infrastructure.persistence.repository.JpaRolPermisoRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaRolPermisoRepositoryAdapter implements RolPermisoRepository {

    private final JpaRolPermisoRepository repository;

    @Override
    public List<RolPermiso> findAllByRolId(Long rolId) {
        return repository.findAllByRolId(rolId).stream().map(this::toDomain).toList();
    }

    @Override
    public List<RolPermiso> findAllByPermisoId(Long permisoId) {
        return repository.findAllByPermisoId(permisoId).stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<RolPermiso> findByRolIdAndPermisoId(Long rolId, Long permisoId) {
        return repository.findByRolIdAndPermisoId(rolId, permisoId).map(this::toDomain);
    }

    @Override
    public RolPermiso save(RolPermiso rolPermiso) {
        return toDomain(repository.save(toEntity(rolPermiso)));
    }

    @Override
    public void deleteById(Long rolPermisoId) {
        repository.deleteById(rolPermisoId);
    }

    @Override
    public void deleteByRolIdAndPermisoId(Long rolId, Long permisoId) {
        repository.deleteByRolIdAndPermisoId(rolId, permisoId);
    }

    private RolPermiso toDomain(RolPermisoEntity entity) {
        return RolPermiso.builder()
                .rolPermisoId(entity.getRolPermisoId())
                .rolId(entity.getRolId())
                .permisoId(entity.getPermisoId())
                .build();
    }

    private RolPermisoEntity toEntity(RolPermiso rolPermiso) {
        return RolPermisoEntity.builder()
                .rolPermisoId(rolPermiso.getRolPermisoId())
                .rolId(rolPermiso.getRolId())
                .permisoId(rolPermiso.getPermisoId())
                .build();
    }
}
