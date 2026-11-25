package um.haberes.core.hexagonal.seguridad.infrastructure.persistence.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import um.haberes.core.hexagonal.seguridad.domain.model.UsuarioPermiso;
import um.haberes.core.hexagonal.seguridad.domain.ports.out.UsuarioPermisoRepository;
import um.haberes.core.hexagonal.seguridad.infrastructure.persistence.entity.UsuarioPermisoEntity;
import um.haberes.core.hexagonal.seguridad.infrastructure.persistence.repository.JpaUsuarioPermisoRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaUsuarioPermisoRepositoryAdapter implements UsuarioPermisoRepository {

    private final JpaUsuarioPermisoRepository repository;

    @Override
    public List<UsuarioPermiso> findAllByLegajoId(Long legajoId) {
        return repository.findAllByLegajoId(legajoId).stream().map(this::toDomain).toList();
    }

    @Override
    public List<UsuarioPermiso> findAllByPermisoId(Long permisoId) {
        return repository.findAllByPermisoId(permisoId).stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<UsuarioPermiso> findByLegajoIdAndPermisoId(Long legajoId, Long permisoId) {
        return repository.findByLegajoIdAndPermisoId(legajoId, permisoId).map(this::toDomain);
    }

    @Override
    public UsuarioPermiso save(UsuarioPermiso usuarioPermiso) {
        return toDomain(repository.save(toEntity(usuarioPermiso)));
    }

    @Override
    public void deleteById(Long usuarioPermisoId) {
        repository.deleteById(usuarioPermisoId);
    }

    @Override
    public void deleteByLegajoIdAndPermisoId(Long legajoId, Long permisoId) {
        repository.deleteByLegajoIdAndPermisoId(legajoId, permisoId);
    }

    private UsuarioPermiso toDomain(UsuarioPermisoEntity entity) {
        return UsuarioPermiso.builder()
                .usuarioPermisoId(entity.getUsuarioPermisoId())
                .legajoId(entity.getLegajoId())
                .permisoId(entity.getPermisoId())
                .otorgado(entity.getOtorgado())
                .build();
    }

    private UsuarioPermisoEntity toEntity(UsuarioPermiso usuarioPermiso) {
        return UsuarioPermisoEntity.builder()
                .usuarioPermisoId(usuarioPermiso.getUsuarioPermisoId())
                .legajoId(usuarioPermiso.getLegajoId())
                .permisoId(usuarioPermiso.getPermisoId())
                .otorgado(usuarioPermiso.getOtorgado())
                .build();
    }
}
