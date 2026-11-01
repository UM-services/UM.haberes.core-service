package um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.out;

import java.util.List;
import java.util.Optional;

import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;

public interface CursoCargoRepository {

    CursoCargo save(CursoCargo cursoCargo);

    List<CursoCargo> saveAll(List<CursoCargo> cursoCargos);

    Optional<CursoCargo> findByCursoCargoId(Long cursoCargoId);

    Optional<CursoCargo> findByCursoIdAndAnhoAndMesAndCargoTipoIdAndLegajoId(Long cursoId, Integer anho,
            Integer mes, Integer cargoTipoId, Long legajoId);

    Optional<CursoCargo> findByCursoIdAndAnhoAndMesAndLegajoId(Long cursoId, Integer anho, Integer mes,
            Long legajoId);

    List<CursoCargo> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

    List<CursoCargo> findAllByLegajoIdAndAnhoAndMesAndCursoNivelId(Long legajoId, Integer anho, Integer mes,
            Integer nivelId);

    List<CursoCargo> findAllByLegajoIdAndAnhoAndMesAndCursoNivelIdIn(Long legajoId, Integer anho, Integer mes,
            List<Integer> nivelIds);

    List<CursoCargo> findAllByLegajoIdAndAnhoAndMesAndDesarraigo(Long legajoId, Integer anho, Integer mes,
            Byte desarraigo);

    List<CursoCargo> findAllByLegajoIdAndAnhoAndMesAndCursoAdicionalCargaHoraria(Long legajoId, Integer anho,
            Integer mes, Byte adicionalCargaHoraria);

    List<CursoCargo> findAllByCursoIdAndAnhoAndMes(Long cursoId, Integer anho, Integer mes);

    List<CursoCargo> findTopByCursoId(Long cursoId);

    List<CursoCargo> findAllByAnhoAndMes(Integer anho, Integer mes);

    List<CursoCargo> findTopByAnhoAndMes(Integer anho, Integer mes);

    List<CursoCargo> findAllByAnhoAndMesAndDesarraigo(Integer anho, Integer mes, Byte desarraigo);

    List<CursoCargo> findAllByCursoIdIn(List<Long> cursoIds);

    List<CursoCargo> findAllByLegajoIdAndAnhoAndMesAndCursoFacultadId(Long legajoId, Integer anho, Integer mes,
            Integer facultadId);

    List<CursoCargo> findAllByLegajoIdAndAnhoAndMesAndCargoTipoIdAndCursoFacultadIdAndCursoGeograficaIdAndCursoAnualAndCursoSemestre1AndCursoSemestre2(
            Long legajoId, Integer anho, Integer mes, Integer cargoTipoId, Integer facultadId, Integer geograficaId,
            Byte anual, Byte semestre1, Byte semestre2);

    void deleteByCursoCargoId(Long cursoCargoId);

    void deleteByCursoIdAndAnhoAndMesAndCargoTipoIdAndLegajoId(Long cursoId, Integer anho, Integer mes,
            Integer cargoTipoId, Long legajoId);
}
