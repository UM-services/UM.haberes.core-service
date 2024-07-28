/**
 * 
 */
package um.haberes.core.exception;

/**
 * @author daniel
 *
 */
public class CategoriaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7710146162234020015L;

	public CategoriaException() {
		super("Could not find Categoria");
	}

	public CategoriaException(Integer categoriaId) {
		super("Could not find Categoria " + categoriaId);
	}

}
