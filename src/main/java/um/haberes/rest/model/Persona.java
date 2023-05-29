/**
 * 
 */
package um.haberes.rest.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.OffsetDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonFormat;

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
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "documento" }) })
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Persona extends Auditable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5909095837268244050L;

	@Id
	private Long legajoId;

	private BigDecimal documento = BigDecimal.ZERO;
	private String apellido = "";
	private String nombre = "";

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime nacimiento;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime altaDocente;

	private Integer ajusteDocente = 0;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime altaAdministrativa;

	private Integer ajusteAdministrativo = 0;
	private String estadoCivil = "";
	private Integer situacionId;
	private Byte reemplazoDesarraigo = 0;
	private Byte mitadDesarraigo = 0;
	private String cuil = "";
	private Integer posgrado = 0;
	private Integer estado = 0;
	private String liquida = "";
	private Integer estadoAfip = 0;
	private Integer dependenciaId;
	private String salida;
	private Long obraSocial;
	private Integer actividadAfip;
	private Integer localidadAfip;
	private Integer situacionAfip;
	private Integer modeloContratacionAfip;

	@OneToOne(optional = true)
	@JoinColumn(name = "dependenciaId", insertable = false, updatable = false)
	private Dependencia dependencia;

	public String getApellidoNombre() {
		return MessageFormat.format("{0}, {1}", this.apellido, this.nombre);
	}

}
