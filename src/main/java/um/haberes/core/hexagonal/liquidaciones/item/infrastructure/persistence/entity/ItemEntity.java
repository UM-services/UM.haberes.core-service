package um.haberes.core.hexagonal.liquidaciones.item.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import um.haberes.core.model.AuditableEntity;
import um.haberes.core.hexagonal.liquidaciones.codigo.infrastructure.persistence.entity.CodigoEntity;
import um.haberes.core.hexagonal.personas.persona.infrastructure.persistence.entity.PersonaEntity;
import um.haberes.core.util.Jsonifyable;

import java.math.BigDecimal;

@Entity
@Table(name = "item", uniqueConstraints = {@UniqueConstraint(columnNames = {"legajoId", "anho", "mes", "codigoId"})})
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ItemEntity extends AuditableEntity implements Jsonifyable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId = null;

    private Long legajoId = null;

    private int anho = 0;

    private int mes = 0;

    private Integer codigoId = null;

    private String codigoNombre = "";

    private BigDecimal importe = BigDecimal.ZERO;

    @OneToOne(optional = true)
    @JoinColumn(name = "legajoId", insertable = false, updatable = false)
    private PersonaEntity persona = null;

    @OneToOne(optional = true)
    @JoinColumn(name = "codigoId", insertable = false, updatable = false)
    private CodigoEntity codigo = null;

    public String legajoKey() {
        return String.valueOf(legajoId) + "." + anho + "." + mes + "." + codigoId;
    }
}
