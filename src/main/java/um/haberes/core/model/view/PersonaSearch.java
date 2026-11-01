package um.haberes.core.model.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Immutable;
import um.haberes.core.model.AuditableEntity;
import um.haberes.core.hexagonal.personas.dependencia.infrastructure.persistence.entity.DependenciaEntity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "vw_persona_search")
@Immutable
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PersonaSearch extends AuditableEntity {

    @Id
    private Long legajoId = null;

    private BigDecimal documento = BigDecimal.ZERO;

    private String apellido = "";

    private String nombre = "";

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime nacimiento = null;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime altaDocente = null;

    private int ajusteDocente = 0;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime altaAdministrativa = null;

    private int ajusteAdministrativo = 0;

    private String estadoCivil = "";

    private Integer situacionId = null;

    private Byte reemplazoDesarraigo = 0;

    private Byte mitadDesarraigo = 0;

    private String cuil = "";

    private int posgrado = 0;

    private int estado = 1;

    private String liquida = "S";

    private int estadoAfip = 1;

    private Integer dependenciaId = null;

    private String salida = null;

    private Long obraSocial = null;

    private Integer actividadAfip = null;

    private Integer localidadAfip = null;

    private Integer situacionAfip = null;

    private Integer modeloContratacionAfip = null;

    private String search = null;

    @OneToOne
    @JoinColumn(name = "dependenciaId", insertable = false, updatable = false)
    private DependenciaEntity dependencia = null;
}
