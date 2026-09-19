package um.haberes.core.hexagonal.cursos.curso.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso.domain.model.Curso;
import um.haberes.core.hexagonal.cursos.curso.domain.ports.out.CursoRepository;
import um.haberes.core.hexagonal.cursos.curso.infrastructure.persistence.entity.CursoEntity;
import um.haberes.core.hexagonal.cursos.curso.infrastructure.persistence.mapper.CursoMapper;
import um.haberes.core.hexagonal.cursos.curso.infrastructure.persistence.repository.JpaCursoRepository;

@Component
@RequiredArgsConstructor
public class JpaCursoRepositoryAdapter implements CursoRepository {

    private static final Sort NOMBRE_ORDER = Sort.by("nombre").ascending();

    private final JpaCursoRepository jpaCursoRepository;
    private final CursoMapper cursoMapper;

    @Override
    public List<Curso> findAll() {
        return toDomainList(jpaCursoRepository.findAll());
    }

    @Override
    public List<Curso> findAllByFacultadIdAndGeograficaIdAndConditions(Integer facultadId, Integer geograficaId,
            List<String> conditions) {
        return toDomainList(jpaCursoRepository.findAllByFacultadIdAndGeograficaIdAndConditions(facultadId,
                geograficaId, conditions));
    }

    @Override
    public List<Curso> findAllByFacultadIdAndGeograficaId(Integer facultadId, Integer geograficaId) {
        return toDomainList(
                jpaCursoRepository.findAllByFacultadIdAndGeograficaId(facultadId, geograficaId, NOMBRE_ORDER));
    }

    @Override
    public List<Curso> findAllByCursoIdIn(List<Long> cursoIds) {
        return toDomainList(jpaCursoRepository.findAllByCursoIdIn(cursoIds));
    }

    @Override
    public List<Curso> findAllByFacultadId(Integer facultadId) {
        return toDomainList(jpaCursoRepository.findAllByFacultadId(facultadId));
    }

    @Override
    public List<Curso> findAllByFacultadIdAndGeograficaIdAndCursoIdIn(Integer facultadId, Integer geograficaId,
            List<Long> cursoIds) {
        return toDomainList(jpaCursoRepository.findAllByFacultadIdAndGeograficaIdAndCursoIdInOrderByNombre(facultadId,
                geograficaId, cursoIds));
    }

    @Override
    public Optional<Curso> findByCursoId(Long cursoId) {
        return jpaCursoRepository.findByCursoId(cursoId).map(cursoMapper::toDomain);
    }

    @Override
    public Curso save(Curso curso) {
        return cursoMapper.toDomain(jpaCursoRepository.save(cursoMapper.toEntity(curso)));
    }

    @Override
    public void deleteByCursoId(Long cursoId) {
        jpaCursoRepository.deleteByCursoId(cursoId);
    }

    private List<Curso> toDomainList(List<CursoEntity> entities) {
        return entities.stream()
                .map(cursoMapper::toDomain)
                .collect(Collectors.toList());
    }
}
