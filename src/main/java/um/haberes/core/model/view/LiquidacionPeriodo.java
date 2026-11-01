package um.haberes.core.model.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Immutable;
import um.haberes.core.model.AuditableEntity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "vw_liquidacion_periodo")
@Immutable
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LiquidacionPeriodo extends AuditableEntity {

    @Id
    private Long liquidacionId = null;

    private Long legajoId = null;

    private int anho = 0;

    private int mes = 0;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaLiquidacion = null;

    private Integer dependenciaId = null;

    private String salida = null;

    private BigDecimal totalRemunerativo = BigDecimal.ZERO;

    private BigDecimal totalNoRemunerativo = BigDecimal.ZERO;

    private BigDecimal totalDeduccion = BigDecimal.ZERO;

    private BigDecimal totalNeto = BigDecimal.ZERO;

    private Byte bloqueado = 0;

    private Long periodo = null;
}
