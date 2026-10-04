package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in;

public interface DeleteCargosByPeriodoUseCase {

    void deleteCargosByPeriodo(Integer anho, Integer mes);
}
