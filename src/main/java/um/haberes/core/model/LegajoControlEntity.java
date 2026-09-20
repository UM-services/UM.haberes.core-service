package um.haberes.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import um.haberes.core.hexagonal.personas.persona.infrastructure.persistence.entity.PersonaEntity;

@Entity
@Table(name = "legajo_control", uniqueConstraints = {@UniqueConstraint(columnNames = {"legajoId", "anho", "mes"})})
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LegajoControlEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long legajoControlId = null;

    private Long legajoId = null;

    private int anho = 0;

    private int mes = 0;

    private Byte liquidado = 0;

    private Byte fusionado = 0;

    private Byte bonoEnviado = 0;

    @OneToOne(optional = true)
    @JoinColumn(name = "legajoId", insertable = false, updatable = false)
    private PersonaEntity persona = null;
}
