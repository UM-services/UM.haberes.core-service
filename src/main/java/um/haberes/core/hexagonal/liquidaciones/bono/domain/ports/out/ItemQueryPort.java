package um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out;

public interface ItemQueryPort {

    boolean existsByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);
}
