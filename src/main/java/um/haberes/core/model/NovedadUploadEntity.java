package um.haberes.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import um.haberes.core.hexagonal.liquidaciones.codigo.infrastructure.persistence.entity.CodigoEntity;
import um.haberes.core.hexagonal.personas.dependencia.infrastructure.persistence.entity.DependenciaEntity;
import um.haberes.core.hexagonal.personas.persona.infrastructure.persistence.entity.PersonaEntity;

import java.math.BigDecimal;

@Entity
@Table(name = "novedad_upload")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NovedadUploadEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long novedadUploadId = null;

    private Long legajoId = null;

    private int anho = 0;

    private int mes = 0;

    private Integer codigoId = null;

    private Integer dependenciaId = null;

    private BigDecimal importe = BigDecimal.ZERO;

    @Column(name = "`value`")
    private String value = "";

    private Byte pendiente = 0;

    @OneToOne(optional = true)
    @JoinColumn(name = "legajoId", insertable = false, updatable = false)
    private PersonaEntity persona = null;

    @OneToOne(optional = true)
    @JoinColumn(name = "codigoId", insertable = false, updatable = false)
    private CodigoEntity codigo = null;

    @OneToOne(optional = true)
    @JoinColumn(name = "dependenciaId", insertable = false, updatable = false)
    private DependenciaEntity dependencia = null;
}
