/**
 * 
 */
package ar.edu.um.haberes.rest.model.view.pk;

import java.io.Serializable;

import lombok.Data;

/**
 * @author daniel
 *
 */
@Data
public class AsignadoCategoriaPk implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -1548238109445393548L;

	private Long legajoId;
	private Integer dependenciaId;
	private Integer categoriaId;

}
