/**
 * 
 */
package um.haberes.core.exception;

/**
 * @author daniel
 *
 */
public class CategoriaImputacionException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8174057896164325314L;

	public CategoriaImputacionException(Long categoriaimputacionId) {
		super("Cannot find CategoriaImputacion " + categoriaimputacionId);
	}

	public CategoriaImputacionException(Integer dependenciaId, Integer facultadId, Integer geograficaId,
										Integer categoriaId) {
		super("Cannot find CategoriaImputacion " + dependenciaId + "/" + facultadId + "/" + geograficaId + "/"
				+ categoriaId);
	}

}
