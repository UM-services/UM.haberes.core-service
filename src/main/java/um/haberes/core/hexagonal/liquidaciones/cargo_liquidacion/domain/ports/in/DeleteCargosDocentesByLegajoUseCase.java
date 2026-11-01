package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in;

public interface DeleteCargosDocentesByLegajoUseCase {

    void deleteCargosDocentesByLegajo(Long legajoId, Integer anho, Integer mes);
}
