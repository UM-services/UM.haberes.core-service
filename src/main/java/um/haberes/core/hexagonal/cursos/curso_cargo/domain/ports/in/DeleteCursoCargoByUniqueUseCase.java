package um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in;

public interface DeleteCursoCargoByUniqueUseCase {

    void deleteCursoCargoByUnique(Long cursoId, Integer anho, Integer mes, Integer cargoTipoId, Long legajoId);
}
