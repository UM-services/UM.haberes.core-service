package um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import um.haberes.core.model.AuditableEntity;

import java.math.BigDecimal;

@Entity
@Table(name = "legajo_contabilidad", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "legajoId", "anho", "mes" }) })
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LegajoContabilidadEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long legajoContabilidadId;

    private Long legajoId;

    private int anho;

    private int mes;

    @Builder.Default
    private Byte diferencia = 0;

    @Builder.Default
    private BigDecimal remunerativo = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal noRemunerativo = BigDecimal.ZERO;
}
