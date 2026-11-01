package um.haberes.core.hexagonal.cursos.curso_fusion.domain.ports.in;

public interface DeleteCursoFusionsByLegajoUseCase {

    void deleteCursoFusionsByLegajo(Long legajoId, Integer anho, Integer mes);
}
