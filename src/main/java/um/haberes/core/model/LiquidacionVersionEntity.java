package um.haberes.core.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "liquidacion_version", uniqueConstraints = {@UniqueConstraint(columnNames = {"legajoId", "anho", "mes", "version"})})
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LiquidacionVersionEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long liquidacionVersionId = null;

    private Long legajoId = null;

    private int anho = 0;

    private int mes = 0;

    private int version = 0;

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
}
