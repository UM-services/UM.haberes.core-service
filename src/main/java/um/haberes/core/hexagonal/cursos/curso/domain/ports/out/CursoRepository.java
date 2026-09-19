package um.haberes.core.hexagonal.cursos.curso.domain.ports.out;

import java.util.List;
import java.util.Optional;

import um.haberes.core.hexagonal.cursos.curso.domain.model.Curso;

public interface CursoRepository {

    List<Curso> findAll();

    List<Curso> findAllByFacultadIdAndGeograficaIdAndConditions(Integer facultadId, Integer geograficaId,
            List<String> conditions);

    List<Curso> findAllByFacultadIdAndGeograficaId(Integer facultadId, Integer geograficaId);

    List<Curso> findAllByCursoIdIn(List<Long> cursoIds);

    List<Curso> findAllByFacultadId(Integer facultadId);

    List<Curso> findAllByFacultadIdAndGeograficaIdAndCursoIdIn(Integer facultadId, Integer geograficaId,
            List<Long> cursoIds);

    Optional<Curso> findByCursoId(Long cursoId);

    Curso save(Curso curso);

    void deleteByCursoId(Long cursoId);
}
