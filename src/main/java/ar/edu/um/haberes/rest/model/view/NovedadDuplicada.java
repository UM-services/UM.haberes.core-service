/**
 * 
 */
package ar.edu.um.haberes.rest.model.view;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.Data;

/**
 * @author daniel
 *
 */
@Data
@Entity
@Table(name = "vw_novedad_duplicada")
@Immutable
public class NovedadDuplicada implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3079597245799350630L;

	@Id
	private String unified;
	
	private Long legajoId;
	private Integer anho;
	private Integer mes;
	private Integer codigoId;
	private Integer cantidad;

}
