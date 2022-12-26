/**
 * 
 */
package ar.edu.um.haberes.rest.model.view;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Immutable
@Table(name = "vw_legajo_curso_cantidad")
public class LegajoCursoCantidad implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 3357658398144165069L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String uniqueId;

	private Integer anho;
	private Integer mes;
	private Long legajoId;
	private Integer facultadId;
	private Integer geograficaId;
	private Integer anuales;
	private Integer semestre1;
	private Integer semestre2;

}
