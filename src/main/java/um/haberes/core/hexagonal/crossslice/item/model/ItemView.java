package um.haberes.core.hexagonal.crossslice.item.model;

import java.math.BigDecimal;

/** Vista de lectura de un ítem de liquidación, para consumo de otros slices. */
public record ItemView(Long itemId, Integer codigoId, String codigoNombre, BigDecimal importe) {
}
