/**
 * 
 */
package um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class CategoriaImputacionNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8174057896164325314L;

	public CategoriaImputacionNotFoundException(Long categoriaimputacionId) {
		super("Cannot find CategoriaImputacion " + categoriaimputacionId);
	}

	public CategoriaImputacionNotFoundException(Integer dependenciaId, Integer facultadId, Integer geograficaId,
			Integer categoriaId) {
		super("Cannot find CategoriaImputacion " + dependenciaId + "/" + facultadId + "/" + geograficaId + "/"
				+ categoriaId);
	}

}
