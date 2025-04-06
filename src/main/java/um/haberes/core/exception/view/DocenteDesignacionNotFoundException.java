package um.haberes.core.exception.view;

public class DocenteDesignacionNotFoundException extends RuntimeException {
    public DocenteDesignacionNotFoundException(Integer anho, Integer mes) {
        super("Cannot find DocenteDesignacion " + mes + "/" + anho);
    }

}
