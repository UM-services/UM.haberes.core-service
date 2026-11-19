package um.haberes.core.hexagonal.crossslice.cargos_clase.model;

import java.math.BigDecimal;

/** Vista de lectura de un cargo con clase, para consumo de otros slices. */
public record CargoConClaseView(Long cargoClaseDetalleId, Integer anho, Integer mes, String descripcion,
                                String facultadReducido, String sedeNombre, Long periodoDesde,
                                Long periodoHasta, int horas, BigDecimal valorHora) {
}
