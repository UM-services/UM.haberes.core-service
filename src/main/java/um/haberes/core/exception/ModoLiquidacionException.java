package um.haberes.core.exception;

public class ModoLiquidacionException extends RuntimeException {

    public ModoLiquidacionException(Integer modoLiquidacionId) {
        super("Cannot find ModoLiquidacion modoLiquidacionId="+ modoLiquidacionId);
    }

}
