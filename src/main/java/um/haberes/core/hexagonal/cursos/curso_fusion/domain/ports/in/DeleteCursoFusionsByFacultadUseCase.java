package um.haberes.core.hexagonal.cursos.curso_fusion.domain.ports.in;

public interface DeleteCursoFusionsByFacultadUseCase {

    void deleteCursoFusionsByFacultad(Long legajoId, Integer anho, Integer mes, Integer facultadId,
            Integer geograficaId);
}
