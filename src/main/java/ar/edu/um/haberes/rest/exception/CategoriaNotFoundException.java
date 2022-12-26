/**
 * 
 */
package ar.edu.um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class CategoriaNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7710146162234020015L;

	public CategoriaNotFoundException() {
		super("Could not find Categoria");
	}

	public CategoriaNotFoundException(Integer categoriaId) {
		super("Could not find Categoria " + categoriaId);
	}

}
