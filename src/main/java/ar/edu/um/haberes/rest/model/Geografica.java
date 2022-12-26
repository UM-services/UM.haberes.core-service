/**
 * 
 */
package ar.edu.um.haberes.rest.model;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author dquinteros
 *
 */
@Entity
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Geografica extends Auditable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 856856435059176231L;

	@Id
	private Integer geograficaId;
	
	private String nombre = "";
	private String reducido = "";
	private BigDecimal desarraigo = BigDecimal.ZERO;
	private Integer geograficaIdReemplazo;

}
