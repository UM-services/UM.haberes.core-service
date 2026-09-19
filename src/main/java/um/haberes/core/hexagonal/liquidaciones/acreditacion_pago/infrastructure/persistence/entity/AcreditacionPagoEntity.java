package um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.infrastructure.persistence.entity;

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
@Table(name = "acreditacion_pago", uniqueConstraints = { @UniqueConstraint(columnNames = { "anho", "mes", "fechaPago" }) })
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AcreditacionPagoEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long acreditacionPagoId;

    private int anho;

    private int mes;

    private OffsetDateTime fechaPago;

    @Builder.Default
    private BigDecimal totalSantander = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal totalOtrosBancos = BigDecimal.ZERO;

    private Integer comprobanteIdPago;

    private Integer puntoVentaPago;

    private Long numeroComprobantePago;
}
