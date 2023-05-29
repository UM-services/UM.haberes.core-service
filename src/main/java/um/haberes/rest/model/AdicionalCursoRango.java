/**
 * 
 */
package um.haberes.rest.model;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author daniel
 *
 */
@Entity
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AdicionalCursoRango extends Auditable implements Serializable  {

	private static final long serialVersionUID = 2960349518526001791L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long adicionalCursoRangoId;
	
	private Integer horasDesde = 0;
	private Integer horasHasta = 0;
	private BigDecimal porcentaje = BigDecimal.ZERO;
	private Long adicionalCursoTablaId;

}
