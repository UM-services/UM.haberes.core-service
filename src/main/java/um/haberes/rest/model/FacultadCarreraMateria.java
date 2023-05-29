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
import jakarta.persistence.JoinColumns;
import jakarta.persistence.OneToOne;
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
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "facultadId", "planId", "carreraId", "materiaId" }) })
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FacultadCarreraMateria extends Auditable implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 9020765877839550829L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long facultadCarreraMateriaId;

	private Integer facultadId;
	private Integer planId;
	private Integer carreraId;
	private String materiaId;

	@OneToOne
	@JoinColumns({
			@JoinColumn(name = "facultadId", referencedColumnName = "facultadId", insertable = false, updatable = false),
			@JoinColumn(name = "planId", referencedColumnName = "planId", insertable = false, updatable = false),
			@JoinColumn(name = "carreraId", referencedColumnName = "carreraId", insertable = false, updatable = false) })
	private FacultadCarrera facultadCarrera;

	@OneToOne
	@JoinColumns({
			@JoinColumn(name = "facultadId", referencedColumnName = "facultadId", insertable = false, updatable = false),
			@JoinColumn(name = "planId", referencedColumnName = "planId", insertable = false, updatable = false),
			@JoinColumn(name = "materiaId", referencedColumnName = "materiaId", insertable = false, updatable = false) })
	private FacultadMateria facultadMateria;

}
