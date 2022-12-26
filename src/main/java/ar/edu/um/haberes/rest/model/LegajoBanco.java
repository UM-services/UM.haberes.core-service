/**
 * 
 */
package ar.edu.um.haberes.rest.model;

import java.io.Serializable;
import java.math.BigDecimal;

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
import lombok.ToString;

/**
 * @author daniel
 *
 */
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "legajoId", "anho", "mes", "cbu" }) })
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LegajoBanco extends Auditable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6958775655040740839L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long legajoBancoId;

	private Long legajoId;
	private Integer anho = 0;
	private Integer mes = 0;
	private String cbu = "";
	private BigDecimal fijo = BigDecimal.ZERO;
	private BigDecimal porcentaje = BigDecimal.ZERO;
	private Byte resto = 0;
	private BigDecimal acreditado = BigDecimal.ZERO;

	@OneToOne
	@JoinColumn(name = "legajoId", insertable = false, updatable = false)
	private Persona persona;

	@OneToOne
	@JoinColumns({
			@JoinColumn(name = "legajoId", referencedColumnName = "legajoId", insertable = false, updatable = false),
			@JoinColumn(name = "anho", referencedColumnName = "anho", insertable = false, updatable = false),
			@JoinColumn(name = "mes", referencedColumnName = "mes", insertable = false, updatable = false), })
	private Liquidacion liquidacion;

}
