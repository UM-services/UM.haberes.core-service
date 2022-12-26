/**
 * 
 */
package ar.edu.um.haberes.rest.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

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
public class AdicionalCursoTabla extends Auditable implements Serializable {

	private static final long serialVersionUID = 4773233607886477829L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long adicionalCursoTablaId;

	private Long periodoDesde = 0L;
	private Long periodoHasta = 0L;
	private Integer facultadId;

	@OneToOne
	@JoinColumn(name = "facultadId", insertable = false, updatable = false)
	private Facultad facultad;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "adicionalCursoTablaId", insertable = false, updatable = false)
	private List<AdicionalCursoRango> adicionalCursoRangos = null;

}
