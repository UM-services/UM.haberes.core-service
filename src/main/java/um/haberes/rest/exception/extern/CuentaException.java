package um.haberes.rest.exception.extern;

import java.math.BigDecimal;

public class CuentaException extends RuntimeException {

    public CuentaException(BigDecimal numeroCuenta) {
        super("Cannot find Cuenta " + numeroCuenta);
    }

}
