/**
 * 
 */
package ar.edu.um.haberes.rest.model;

import java.io.Serializable;

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
public class Nivel extends Auditable implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -5583622514244521559L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer nivelId;

	private String nombre = "";
	
}
