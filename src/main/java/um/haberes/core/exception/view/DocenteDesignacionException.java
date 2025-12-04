package um.haberes.core.exception.view;

public class DocenteDesignacionException extends RuntimeException {
    public DocenteDesignacionException(Integer anho, Integer mes) {
        super("Cannot find DocenteDesignacion " + mes + "/" + anho);
    }

}
