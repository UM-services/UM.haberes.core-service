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
public class Categoria extends Auditable implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -7627833487679606596L;

	@Id
	private Integer categoriaId;

	private String nombre = "";
	private BigDecimal basico = BigDecimal.ZERO;
	private Byte docente = 0;
	private Byte noDocente = 0;
	private Byte liquidaPorHora = 0;

}
