/**
 * 
 */
package um.haberes.rest.model.view.pk;

import java.io.Serializable;

import lombok.Data;

/**
 * @author daniel
 *
 */
@Data
public class CodigoPeriodoPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2814037504867343031L;

	private Integer anho;
	private Integer mes;
	private Integer codigoId;
}
