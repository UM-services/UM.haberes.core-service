package um.haberes.core.hexagonal.seguridad.infrastructure.persistence.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import um.haberes.core.hexagonal.seguridad.domain.model.UsuarioRol;
import um.haberes.core.hexagonal.seguridad.domain.ports.out.UsuarioRolRepository;
import um.haberes.core.hexagonal.seguridad.infrastructure.persistence.entity.UsuarioRolEntity;
import um.haberes.core.hexagonal.seguridad.infrastructure.persistence.repository.JpaUsuarioRolRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaUsuarioRolRepositoryAdapter implements UsuarioRolRepository {

    private final JpaUsuarioRolRepository repository;

    @Override
    public List<UsuarioRol> findAllByLegajoId(Long legajoId) {
        return repository.findAllByLegajoId(legajoId).stream().map(this::toDomain).toList();
    }

    @Override
    public List<UsuarioRol> findAllByRolId(Long rolId) {
        return repository.findAllByRolId(rolId).stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<UsuarioRol> findByLegajoIdAndRolId(Long legajoId, Long rolId) {
        return repository.findByLegajoIdAndRolId(legajoId, rolId).map(this::toDomain);
    }

    @Override
    public boolean existsByLegajoIdAndRolId(Long legajoId, Long rolId) {
        return repository.existsByLegajoIdAndRolId(legajoId, rolId);
    }

    @Override
    public UsuarioRol save(UsuarioRol usuarioRol) {
        return toDomain(repository.save(toEntity(usuarioRol)));
    }

    @Override
    public void deleteById(Long usuarioRolId) {
        repository.deleteById(usuarioRolId);
    }

    @Override
    public void deleteByLegajoIdAndRolId(Long legajoId, Long rolId) {
        repository.deleteByLegajoIdAndRolId(legajoId, rolId);
    }

    private UsuarioRol toDomain(UsuarioRolEntity entity) {
        return UsuarioRol.builder()
                .usuarioRolId(entity.getUsuarioRolId())
                .legajoId(entity.getLegajoId())
                .rolId(entity.getRolId())
                .build();
    }

    private UsuarioRolEntity toEntity(UsuarioRol usuarioRol) {
        return UsuarioRolEntity.builder()
                .usuarioRolId(usuarioRol.getUsuarioRolId())
                .legajoId(usuarioRol.getLegajoId())
                .rolId(usuarioRol.getRolId())
                .build();
    }
}
