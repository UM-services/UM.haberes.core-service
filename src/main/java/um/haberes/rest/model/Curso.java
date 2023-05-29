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
public class Curso extends Auditable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1540771929062166596L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cursoId;

	private String nombre = "";
	private Integer facultadId;
	private Integer geograficaId;
	private Byte anual;
	private Byte semestre1;
	private Byte semestre2;
	private Integer nivelId;
	private Byte adicionalCargaHoraria;

	@OneToOne
	@JoinColumn(name = "facultadId", insertable = false, updatable = false)
	private Facultad facultad;

	@OneToOne
	@JoinColumn(name = "geograficaId", insertable = false, updatable = false)
	private Geografica geografica;
	
	@OneToOne
	@JoinColumn(name = "nivelId", insertable = false, updatable = false)
	private Nivel nivel;

}
