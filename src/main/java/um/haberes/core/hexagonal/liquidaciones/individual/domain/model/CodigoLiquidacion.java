package um.haberes.core.hexagonal.liquidaciones.individual.domain.model;

/**
 * Códigos del catálogo que consumen las reglas de liquidación individual.
 *
 * <p>Es un contrato propio del slice con el catálogo de códigos; un test verifica que cada valor
 * siga existiendo en el catálogo real.
 */
public enum CodigoLiquidacion {

    AGUINALDO(3),
    PRESENTISMO(16),
    AGUINALDO_ETEC(29),
    VACACIONES(57);

    private final int codigoId;

    CodigoLiquidacion(int codigoId) {
        this.codigoId = codigoId;
    }

    public int codigoId() {
        return codigoId;
    }

    public boolean coincideCon(Integer codigoId) {
        return codigoId != null && codigoId == this.codigoId;
    }
}
