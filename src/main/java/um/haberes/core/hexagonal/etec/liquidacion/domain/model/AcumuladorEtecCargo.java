package um.haberes.core.hexagonal.etec.liquidacion.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import lombok.Getter;

/**
 * Acumula los conceptos de los cargos ETEC de un mismo origen: valor del cargo, antigüedad,
 * presentismo, adicional carrera, arraigo y horas de clase. Cada importe se redondea por cargo,
 * igual que el resto del motor. El origen (puntos u horas) lo decide {@link AcumuladorEtec}.
 */
@Getter
public class AcumuladorEtecCargo {

    private BigDecimal cargo = BigDecimal.ZERO;

    private BigDecimal antiguedad = BigDecimal.ZERO;

    private BigDecimal presentismo = BigDecimal.ZERO;

    private BigDecimal adicionalCarrera = BigDecimal.ZERO;

    private BigDecimal arraigo = BigDecimal.ZERO;

    /** Horas de clase semanales del origen: sólo el cargo por horas las aporta. */
    private BigDecimal horas = BigDecimal.ZERO;

    /**
     * Acumula un cargo sin horas de clase. El porcentaje de arraigo sale de la escala global según
     * la antigüedad docente del legajo (puede ser cero si no alcanza el primer tramo).
     */
    public void acumular(BigDecimal cargoCargo, BigDecimal antiguedadCargo,
                         BigDecimal porcentajePresentismo, BigDecimal porcentajeAdicionalCarrera,
                         BigDecimal porcentajeArraigo) {
        acumular(cargoCargo, antiguedadCargo, porcentajePresentismo, porcentajeAdicionalCarrera,
                porcentajeArraigo, BigDecimal.ZERO);
    }

    /** Acumula un cargo con las horas de clase que le dieron origen. */
    public void acumular(BigDecimal cargoCargo, BigDecimal antiguedadCargo,
                         BigDecimal porcentajePresentismo, BigDecimal porcentajeAdicionalCarrera,
                         BigDecimal porcentajeArraigo, BigDecimal horasCargo) {
        if (horasCargo != null && horasCargo.compareTo(BigDecimal.ZERO) > 0) {
            this.horas = this.horas.add(horasCargo);
        }
        if (cargoCargo == null || cargoCargo.compareTo(BigDecimal.ZERO) == 0) {
            return;
        }
        BigDecimal antiguedad = antiguedadCargo == null ? BigDecimal.ZERO : antiguedadCargo;
        this.cargo = this.cargo.add(cargoCargo);
        this.antiguedad = this.antiguedad.add(antiguedad);
        this.presentismo = this.presentismo.add(porcentaje(cargoCargo, porcentajePresentismo));
        this.adicionalCarrera = this.adicionalCarrera.add(
                porcentaje(cargoCargo.add(antiguedad), porcentajeAdicionalCarrera));
        this.arraigo = this.arraigo.add(porcentaje(cargoCargo, porcentajeArraigo));
    }

    public boolean hayCargos() {
        return cargo.compareTo(BigDecimal.ZERO) > 0;
    }

    private BigDecimal porcentaje(BigDecimal base, BigDecimal porcentaje) {
        if (porcentaje == null) {
            return BigDecimal.ZERO;
        }
        return base.multiply(porcentaje).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
    }
}
