package um.haberes.core.hexagonal.liquidaciones.general.domain.model;

import java.math.BigDecimal;

/** Totales del padrón de liquidación general. */
public record ResumenPadron(int total, int docentes, int noDocentes, int conNovedades, int liquidados,
                            int bloqueados, int netoNegativo, int netoCero, BigDecimal totalNeto) {
}
