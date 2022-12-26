/**
 * 
 */
package ar.edu.um.haberes.rest.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

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
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "facultadId", "planId", "materiaId" }) })
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FacultadMateria extends Auditable implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -3741154790661177568L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long facultadMateriaId;

	private Integer facultadId;
	private Integer planId;
	private String materiaId;
	private String nombre = "";

//	@OneToOne
//	@JoinColumns({
//			@JoinColumn(name = "facultadId", referencedColumnName = "facultadId", insertable = false, updatable = false),
//			@JoinColumn(name = "planId", referencedColumnName = "planId", insertable = false, updatable = false) })
//	private FacultadMateria facultadMateria;

}
