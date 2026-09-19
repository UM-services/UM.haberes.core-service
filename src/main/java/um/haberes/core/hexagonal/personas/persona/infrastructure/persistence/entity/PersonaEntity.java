package um.haberes.core.hexagonal.personas.persona.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import um.haberes.core.model.AfipSituacionEntity;
import um.haberes.core.model.AuditableEntity;
import um.haberes.core.hexagonal.personas.dependencia.infrastructure.persistence.entity.DependenciaEntity;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.OffsetDateTime;

@Entity
@Table(name = "persona", uniqueConstraints = {@UniqueConstraint(columnNames = {"documento"})})
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonaEntity extends AuditableEntity {

    @Id
    private Long legajoId;

    @Builder.Default
    private BigDecimal documento = BigDecimal.ZERO;

    @Builder.Default
    private String apellido = "";

    @Builder.Default
    private String nombre = "";

    private OffsetDateTime nacimiento;

    private OffsetDateTime altaDocente;

    @Builder.Default
    private int ajusteDocente = 0;

    private OffsetDateTime altaAdministrativa;

    @Builder.Default
    private int ajusteAdministrativo = 0;

    @Builder.Default
    private String estadoCivil = "";

    private Integer situacionId;

    @Builder.Default
    private Byte reemplazoDesarraigo = 0;

    @Builder.Default
    private Byte mitadDesarraigo = 0;

    @Builder.Default
    private String cuil = "";

    @Builder.Default
    private int posgrado = 0;

    @Builder.Default
    private int estado = 0;

    @Builder.Default
    private String liquida = "";

    @Builder.Default
    private int estadoAfip = 0;

    private Integer dependenciaId;

    private String salida;

    private Long obraSocial;

    private Integer actividadAfip;

    private Integer localidadAfip;

    @Builder.Default
    private int situacionAfip = 0;

    private Integer modeloContratacionAfip;

    @Builder.Default
    private Byte directivoEtec = 0;

    @OneToOne(optional = true)
    @JoinColumn(name = "dependenciaId", insertable = false, updatable = false)
    private DependenciaEntity dependencia;

    @OneToOne(optional = true)
    @JoinColumn(name = "situacionAfip", insertable = false, updatable = false)
    private AfipSituacionEntity afipSituacion;

    public String getApellidoNombre() {
        return MessageFormat.format("{0}, {1}", this.apellido, this.nombre);
    }
}
