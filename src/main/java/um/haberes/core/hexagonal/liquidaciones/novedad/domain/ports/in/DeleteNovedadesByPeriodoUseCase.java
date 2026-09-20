package um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.in;

public interface DeleteNovedadesByPeriodoUseCase {

    void deleteNovedadesByPeriodo(Integer anho, Integer mes);
}
