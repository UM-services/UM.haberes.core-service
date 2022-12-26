/**
 * 
 */
package ar.edu.um.haberes.rest.model.view;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

import org.hibernate.annotations.Immutable;
import ar.edu.um.haberes.rest.model.view.pk.CodigoPeriodoPK;
import lombok.Data;

/**
 * @author daniel
 *
 */
@Data
@Entity
@Immutable
@Table(name = "vw_codigo_periodo")
@IdClass(CodigoPeriodoPK.class)
public class CodigoPeriodo implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 4612018973560089942L;

	@Id
	private Integer anho;

	@Id
	private Integer mes;

	@Id
	private Integer codigoId;
}
