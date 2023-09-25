package um.haberes.rest.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import um.haberes.rest.kotlin.model.Persona;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "legajoId", "anho", "mes" }) })
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LegajoControl extends Auditable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6710012877796760012L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long legajoControlId;

	private Long legajoId;
	private Integer anho = 0;
	private Integer mes = 0;
	private Byte liquidado = 0;
	private Byte fusionado = 0;
	private Byte bonoEnviado = 0;

	@OneToOne(optional = true)
	@JoinColumn(name = "legajoId", insertable = false, updatable = false)
	private Persona persona;

}
