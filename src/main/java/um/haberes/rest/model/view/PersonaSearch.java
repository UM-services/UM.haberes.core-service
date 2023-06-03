/**
 * 
 */
package um.haberes.rest.model.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonFormat;

import um.haberes.rest.kotlin.model.Dependencia;
import um.haberes.rest.model.Auditable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author daniel
 *
 */
@Data
@Entity
@Table(name = "vw_persona_search")
@Immutable
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class PersonaSearch extends Auditable implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -4674424156602422005L;

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
	private Integer estado = 1;
	private String liquida = "S";
	private Integer estadoAfip = 1;
	private Integer dependenciaId;
	private String salida;
	private Long obraSocial;
	private Integer actividadAfip;
	private Integer localidadAfip;
	private Integer situacionAfip;
	private Integer modeloContratacionAfip;
	private String search;

	@OneToOne
	@JoinColumn(name = "dependenciaId", insertable = false, updatable = false)
	private Dependencia dependencia;

}
