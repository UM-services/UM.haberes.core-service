package um.haberes.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "seguridad_social", uniqueConstraints = {@UniqueConstraint(columnNames = {"anho", "mes"})})
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SeguridadSocialEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seguridadSocialId = null;

    private int anho = 0;

    private int mes = 0;

    private BigDecimal cc351 = BigDecimal.ZERO;

    private BigDecimal cc301 = BigDecimal.ZERO;

    private BigDecimal cc352 = BigDecimal.ZERO;

    private BigDecimal cc302 = BigDecimal.ZERO;

    private BigDecimal cc312 = BigDecimal.ZERO;

    private BigDecimal cc028 = BigDecimal.ZERO;
}
