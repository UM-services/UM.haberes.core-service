package um.haberes.rest.exception.extern;

import java.text.MessageFormat;

public class ProveedorMovimientoException extends RuntimeException {

    public ProveedorMovimientoException(Integer prefijo, Long numeroComprobante) {
        super(MessageFormat.format("Cannot find ProveedorMovimiento -> {0}/{1}", prefijo, numeroComprobante));
    }

    public ProveedorMovimientoException(Integer prefijo){
        super(MessageFormat.format("Cannot find ProveedorMovimiento -> prefijo={0}", prefijo));
    }

}
