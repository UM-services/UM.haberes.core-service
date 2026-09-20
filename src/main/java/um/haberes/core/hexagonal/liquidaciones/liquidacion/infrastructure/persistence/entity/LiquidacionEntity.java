package um.haberes.core.hexagonal.liquidaciones.liquidacion.infrastructure.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import um.haberes.core.model.AuditableEntity;
import um.haberes.core.hexagonal.personas.dependencia.infrastructure.persistence.entity.DependenciaEntity;
import um.haberes.core.hexagonal.personas.persona.infrastructure.persistence.entity.PersonaEntity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "liquidacion", uniqueConstraints = {@UniqueConstraint(columnNames = {"legajoId", "anho", "mes"})})
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LiquidacionEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long liquidacionId = null;

    private Long legajoId = null;

    private int anho = 0;

    private int mes = 0;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaLiquidacion = null;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaAcreditacion = null;

    private Integer dependenciaId = null;

    private String salida = null;

    private BigDecimal totalRemunerativo = BigDecimal.ZERO;

    private BigDecimal totalNoRemunerativo = BigDecimal.ZERO;

    private BigDecimal totalDeduccion = BigDecimal.ZERO;

    private BigDecimal totalNeto = BigDecimal.ZERO;

    private Byte bloqueado = 0;

    private int estado = 0;

    private String liquida = "";

    @OneToOne
    @JoinColumn(name = "legajoId", insertable = false, updatable = false)
    private PersonaEntity persona = null;

    @OneToOne
    @JoinColumn(name = "dependenciaId", insertable = false, updatable = false)
    private DependenciaEntity dependencia = null;

    public String key() {
        return String.valueOf(legajoId) + "." + anho + "." + mes;
    }
}
