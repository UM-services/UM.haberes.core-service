package um.haberes.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import um.haberes.core.hexagonal.personas.dependencia.infrastructure.persistence.entity.DependenciaEntity;
import um.haberes.core.hexagonal.personas.persona.infrastructure.persistence.entity.PersonaEntity;

import java.math.BigDecimal;

@Entity
@Table(name = "liquidacion_adicional")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LiquidacionAdicionalEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long liquidacionAdicionalId = null;

    private Long legajoId = null;

    private Integer anho = null;

    private Integer mes = null;

    private Integer dependenciaId = null;

    private BigDecimal adicional = BigDecimal.ZERO;

    @OneToOne
    @JoinColumn(name = "legajoId", insertable = false, updatable = false)
    private PersonaEntity persona = null;

    @OneToOne
    @JoinColumn(name = "dependenciaId", insertable = false, updatable = false)
    private DependenciaEntity dependencia = null;
}
