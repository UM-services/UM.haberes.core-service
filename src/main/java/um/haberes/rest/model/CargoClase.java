/**
 * 
 */
package um.haberes.rest.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@EqualsAndHashCode(callSuper=false)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CargoClase extends Auditable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4113850296413347255L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cargoClaseId;

	private String nombre = "";
	private Integer claseId;
	
	@OneToOne
	@JoinColumn(name = "claseId", insertable = false, updatable = false)
	private Clase clase;

}
