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
public class CategoriaByPeriodoPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8789481360530450874L;

	private Integer anho;
	private Integer mes;
	private Integer categoriaId;
}
