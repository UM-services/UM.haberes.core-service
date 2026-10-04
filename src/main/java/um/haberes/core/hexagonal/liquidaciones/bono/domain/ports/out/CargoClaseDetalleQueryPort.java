package um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out;

public interface CargoClaseDetalleQueryPort {

    boolean existsByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);
}
