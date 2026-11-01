package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in;

public interface DeleteCargosNoDocentesByLegajoUseCase {

    void deleteCargosNoDocentesByLegajo(Long legajoId, Integer anho, Integer mes);
}
