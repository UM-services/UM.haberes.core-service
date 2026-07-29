package um.haberes.core.hexagonal.facultad.application.exception;

public class FacultadException extends RuntimeException {

	public FacultadException(Integer facultadId) {
		super("Cannot find Facultad " + facultadId);
	}

}
