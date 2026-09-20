package um.haberes.core.hexagonal.liquidaciones.letra.domain.ports.in;

public interface DeleteLetrasByPeriodoUseCase {

    void deleteLetrasByPeriodo(Integer anho, Integer mes);
}
