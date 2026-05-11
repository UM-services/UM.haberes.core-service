package um.haberes.core.hexagonal.geografica.infrastructure.persistence.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import um.haberes.core.exception.GeograficaException;
import um.haberes.core.hexagonal.geografica.domain.model.Geografica;
import um.haberes.core.hexagonal.geografica.domain.ports.out.GeograficaRepository;
import um.haberes.core.hexagonal.geografica.infrastructure.persistence.entity.GeograficaEntity;
import um.haberes.core.hexagonal.geografica.infrastructure.persistence.mapper.GeograficaMapper;
import um.haberes.core.hexagonal.geografica.infrastructure.persistence.repository.JpaGeograficaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaGeograficaRepositoryAdapter implements GeograficaRepository {

    private final JpaGeograficaRepository jpaGeograficaRepository;
    private final GeograficaMapper geograficaMapper;

    @Override
    public Geografica create(Geografica geografica) {
        GeograficaEntity entity = geograficaMapper.toEntity(geografica);
        GeograficaEntity savedEntity = jpaGeograficaRepository.save(entity);
        return geograficaMapper.toDomainModel(savedEntity);
    }

    @Override
    public Geografica findById(Integer id) {
        return jpaGeograficaRepository.findById(id)
                .map(geograficaMapper::toDomainModel)
                .orElseThrow(() -> new GeograficaException(id));
    }

    @Override
    public List<Geografica> findAll() {
        return jpaGeograficaRepository.findAll().stream()
                .map(geograficaMapper::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Geografica> findAllIn(List<Integer> ids) {
        return jpaGeograficaRepository.findAllByGeograficaIdIn(ids).stream()
                .map(geograficaMapper::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Geografica update(Integer id, Geografica geografica) {
        if (jpaGeograficaRepository.existsById(id)) {
            GeograficaEntity entity = geograficaMapper.toEntity(geografica);
            entity.setGeograficaId(id); // Ensure the ID is set for update
            GeograficaEntity updatedEntity = jpaGeograficaRepository.save(entity);
            return geograficaMapper.toDomainModel(updatedEntity);
        }
        throw new GeograficaException(id);
    }

    @Override
    public boolean deleteById(Integer id) {
        if (jpaGeograficaRepository.existsById(id)) {
            jpaGeograficaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
