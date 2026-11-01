package um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.ports.out;

import java.util.List;
import java.util.Optional;

import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.model.CursoDesarraigo;

public interface CursoDesarraigoRepository {

    List<CursoDesarraigo> findAll();

    CursoDesarraigo save(CursoDesarraigo cursoDesarraigo);

    List<CursoDesarraigo> saveAll(List<CursoDesarraigo> cursoDesarraigos);

    Optional<CursoDesarraigo> findByCursoDesarraigoId(Long cursoDesarraigoId);

    List<CursoDesarraigo> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

    List<CursoDesarraigo> findAllByLegajoIdAndAnhoAndMesAndVersion(Long legajoId, Integer anho, Integer mes,
            Integer version);

    Optional<CursoDesarraigo> findByLegajoIdAndAnhoAndMesAndCursoId(Long legajoId, Integer anho, Integer mes,
            Long cursoId);

    void deleteByCursoDesarraigoId(Long cursoDesarraigoId);
}
