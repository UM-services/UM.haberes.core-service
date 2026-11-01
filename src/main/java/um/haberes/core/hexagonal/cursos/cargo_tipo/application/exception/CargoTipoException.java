package um.haberes.core.hexagonal.cursos.cargo_tipo.application.exception;

public class CargoTipoException extends RuntimeException {

	private static final long serialVersionUID = -1396773511411721648L;

	public CargoTipoException() {
		super("Cannot find CargoTipo");
	}

	public CargoTipoException(Integer cargotipoId) {
		super("Cannot find CargoTipo " + cargotipoId);
	}

}
