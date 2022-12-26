/**
 * 
 */
package ar.edu.um.haberes.rest.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author daniel
 *
 */
@Entity
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CodigoGrupo extends Auditable implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -3372892816708081542L;

	@Id
	private Integer codigoId;

	private Byte remunerativo = 0;
	private Byte noRemunerativo = 0;
	private Byte deduccion = 0;
	private Byte total = 0;
	
	@OneToOne
	@JoinColumn(name = "codigoId", insertable = false, updatable = false)
	private Codigo codigo;

}
