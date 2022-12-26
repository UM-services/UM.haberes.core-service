/**
 * 
 */
package ar.edu.um.haberes.rest.model.view;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author daniel
 *
 */
@Data
@Entity
@Table(name = "vw_total_mensual")
@Immutable
@NoArgsConstructor
@AllArgsConstructor
public class TotalMensual implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -5182323785356048364L;

	@Id
	private String uniqueId;

	private Integer anho;
	private Integer mes;
	private Integer codigoId;
	private BigDecimal total = BigDecimal.ZERO;

}
