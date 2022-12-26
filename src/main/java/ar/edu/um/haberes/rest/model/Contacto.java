/**
 * 
 */
package ar.edu.um.haberes.rest.model;

import java.io.Serializable;

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
public class Contacto extends Auditable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6142798960269045664L;

	@Id
	private Long legajoId;

	private String fijo = "";
	private String movil = "";
	private String mailPersonal = "";
	private String mailInstitucional = "";

}
