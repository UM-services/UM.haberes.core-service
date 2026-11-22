package um.haberes.core.hexagonal.etec.liquidacion.domain.model;

import java.math.BigDecimal;

import lombok.Getter;

/**
 * Acumula los conceptos ETEC del legajo separando los dos orígenes de cargo: los cargos por
 * puntos y los cargos por horas de clase (categoría 2002).
 *
 * <p>Cada origen liquida sus propios porcentajes sobre su propia base: lo que se calcula sobre el
 * cargo por puntos pertenece al cargo por puntos y lo que se calcula sobre el cargo por horas
 * pertenece al cargo por horas. Recién al armar los ítems el motor suma los importes del mismo
 * código, igual que acumula los cargos de otras facultades de un docente que dicta en más de una
 * unidad académica.</p>
 *
 * <p>La garantía ETEC, en cambio, es un piso del período: se compara contra la remuneración ETEC
 * completa (ambos orígenes), no contra una parte.</p>
 */
@Getter
public class AcumuladorEtec {

    private final AcumuladorEtecCargo puntos = new AcumuladorEtecCargo();

    private final AcumuladorEtecCargo horas = new AcumuladorEtecCargo();

    public void acumularPuntos(BigDecimal cargoCargo, BigDecimal antiguedadCargo,
                               BigDecimal porcentajePresentismo,
                               BigDecimal porcentajeAdicionalCarrera, BigDecimal porcentajeArraigo) {
        puntos.acumular(cargoCargo, antiguedadCargo, porcentajePresentismo,
                porcentajeAdicionalCarrera, porcentajeArraigo, false);
    }

    public void acumularPuntos(BigDecimal cargoCargo, BigDecimal antiguedadCargo,
                               BigDecimal porcentajePresentismo,
                               BigDecimal porcentajeAdicionalCarrera, BigDecimal porcentajeArraigo,
                               boolean sinPresentismo) {
        puntos.acumular(cargoCargo, antiguedadCargo, porcentajePresentismo,
                porcentajeAdicionalCarrera, porcentajeArraigo, sinPresentismo);
    }

    public void acumularHoras(BigDecimal cargoCargo, BigDecimal antiguedadCargo,
                              BigDecimal porcentajePresentismo,
                              BigDecimal porcentajeAdicionalCarrera, BigDecimal porcentajeArraigo) {
        horas.acumular(cargoCargo, antiguedadCargo, porcentajePresentismo,
                porcentajeAdicionalCarrera, porcentajeArraigo, false);
    }

    public void acumularHoras(BigDecimal cargoCargo, BigDecimal antiguedadCargo,
                              BigDecimal porcentajePresentismo,
                              BigDecimal porcentajeAdicionalCarrera, BigDecimal porcentajeArraigo,
                              boolean sinPresentismo) {
        horas.acumular(cargoCargo, antiguedadCargo, porcentajePresentismo,
                porcentajeAdicionalCarrera, porcentajeArraigo, sinPresentismo);
    }

    /**
     * Acumula un cargo por horas de clase con sus horas semanales: el adicional de carga horaria se
     * liquida sobre ellas con el valor hora del período.
     */
    public void acumularHoras(BigDecimal cargoCargo, BigDecimal antiguedadCargo,
                              BigDecimal porcentajePresentismo,
                              BigDecimal porcentajeAdicionalCarrera, BigDecimal porcentajeArraigo,
                              BigDecimal horasCargo) {
        horas.acumular(cargoCargo, antiguedadCargo, porcentajePresentismo,
                porcentajeAdicionalCarrera, porcentajeArraigo, horasCargo, false);
    }

    public void acumularHoras(BigDecimal cargoCargo, BigDecimal antiguedadCargo,
                              BigDecimal porcentajePresentismo,
                              BigDecimal porcentajeAdicionalCarrera, BigDecimal porcentajeArraigo,
                              BigDecimal horasCargo, boolean sinPresentismo) {
        horas.acumular(cargoCargo, antiguedadCargo, porcentajePresentismo,
                porcentajeAdicionalCarrera, porcentajeArraigo, horasCargo, sinPresentismo);
    }

    public boolean hayCargos() {
        return puntos.hayCargos() || horas.hayCargos();
    }

    public BigDecimal getCargo() {
        return puntos.getCargo().add(horas.getCargo());
    }

    public BigDecimal getAntiguedad() {
        return puntos.getAntiguedad().add(horas.getAntiguedad());
    }

    public BigDecimal getPresentismo() {
        return puntos.getPresentismo().add(horas.getPresentismo());
    }

    /** Presentismo que se descuenta por pérdida de presentismo de los cargos ETEC. */
    public BigDecimal getPresentismoDescuento() {
        return puntos.getPresentismoDescuento().add(horas.getPresentismoDescuento());
    }

    public BigDecimal getAdicionalCarrera() {
        return puntos.getAdicionalCarrera().add(horas.getAdicionalCarrera());
    }

    public BigDecimal getArraigo() {
        return puntos.getArraigo().add(horas.getArraigo());
    }

    /** Horas de clase semanales del legajo: las aporta únicamente el cargo por horas. */
    public BigDecimal getHorasClase() {
        return puntos.getHoras().add(horas.getHoras());
    }
}
