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
public class Build extends Auditable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5193205225344781799L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long build;

}
