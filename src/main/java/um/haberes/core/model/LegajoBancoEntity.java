package um.haberes.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.infrastructure.persistence.entity.LiquidacionEntity;
import um.haberes.core.hexagonal.personas.persona.infrastructure.persistence.entity.PersonaEntity;

import java.math.BigDecimal;

@Entity
@Table(name = "legajo_banco", uniqueConstraints = {@UniqueConstraint(columnNames = {"legajoId", "anho", "mes", "cbu"})})
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LegajoBancoEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long legajoBancoId = null;

    private Long legajoId = null;

    private int anho = 0;

    private int mes = 0;

    private String cbu = "";

    private BigDecimal fijo = BigDecimal.ZERO;

    private BigDecimal porcentaje = BigDecimal.ZERO;

    private Byte resto = 0;

    private BigDecimal acreditado = BigDecimal.ZERO;

    @OneToOne
    @JoinColumn(name = "legajoId", insertable = false, updatable = false)
    private PersonaEntity persona = null;

    @OneToOne
    @JoinColumns({
        @JoinColumn(name = "legajoId", referencedColumnName = "legajoId", insertable = false, updatable = false),
        @JoinColumn(name = "anho", referencedColumnName = "anho", insertable = false, updatable = false),
        @JoinColumn(name = "mes", referencedColumnName = "mes", insertable = false, updatable = false)
    })
    private LiquidacionEntity liquidacion = null;
}
