package um.haberes.core.hexagonal.liquidaciones.acreditacion.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

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

@Entity
@Table(name = "acreditacion", uniqueConstraints = { @UniqueConstraint(columnNames = { "anho", "mes" }) })
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AcreditacionEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long acreditacionId;

    private int anho;

    private int mes;

    @Builder.Default
    private Byte acreditado = 0;

    private OffsetDateTime limiteNovedades;

    private OffsetDateTime fechaContable;

    private Integer ordenContable;

    @Builder.Default
    private BigDecimal sueldosOriginal = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal sueldosAjustados = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal contribucionesPatronales = BigDecimal.ZERO;
}
