package um.haberes.core.hexagonal.facultad.infrastructure.persistence.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import um.haberes.core.exception.FacultadException;
import um.haberes.core.hexagonal.facultad.domain.model.Facultad;
import um.haberes.core.hexagonal.facultad.domain.ports.out.FacultadRepository;
import um.haberes.core.hexagonal.facultad.infrastructure.persistence.entity.FacultadEntity;
import um.haberes.core.hexagonal.facultad.infrastructure.persistence.mapper.FacultadMapper;
import um.haberes.core.hexagonal.facultad.infrastructure.persistence.repository.JpaFacultadRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaFacultadRepositoryAdapter implements FacultadRepository {

    private final JpaFacultadRepository jpaFacultadRepository;
    private final FacultadMapper facultadMapper;

    @Override
    public Facultad create(Facultad facultad) {
        FacultadEntity entity = facultadMapper.toEntity(facultad);
        FacultadEntity savedEntity = jpaFacultadRepository.save(entity);
        return facultadMapper.toDomainModel(savedEntity);
    }

    @Override
    public Facultad findById(Integer id) {
        return jpaFacultadRepository.findByFacultadId(id)
                .map(facultadMapper::toDomainModel)
                .orElseThrow(() -> new FacultadException(id));
    }

    @Override
    public List<Facultad> findAll() {
        return jpaFacultadRepository.findAll().stream()
                .map(facultadMapper::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Facultad> findAllIn(List<Integer> ids) {
        return jpaFacultadRepository.findAllByFacultadIdIn(ids).stream()
                .map(facultadMapper::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Facultad update(Integer id, Facultad facultad) {
        if (jpaFacultadRepository.existsById(id)) {
            FacultadEntity entity = facultadMapper.toEntity(facultad);
            entity.setFacultadId(id); // Ensure the ID is set for update
            FacultadEntity updatedEntity = jpaFacultadRepository.save(entity);
            return facultadMapper.toDomainModel(updatedEntity);
        }
        throw new FacultadException(id);
    }

    @Override
    public boolean deleteById(Integer id) {
        if (jpaFacultadRepository.existsById(id)) {
            jpaFacultadRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
