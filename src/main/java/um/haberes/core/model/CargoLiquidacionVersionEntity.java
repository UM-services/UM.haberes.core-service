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
@Table(name = "cargo_liquidacion_version")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CargoLiquidacionVersionEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cargoLiquidacionVersionId = null;

    private Long legajoId = null;

    private Integer anho = null;

    private Integer mes = null;

    private Integer version = 0;

    private Integer dependenciaId = null;

    private Integer categoriaId = null;

    private BigDecimal basico = BigDecimal.ZERO;

    private BigDecimal estadoDocente = BigDecimal.ZERO;

    private BigDecimal horasJornada = BigDecimal.ZERO;

    private int jornada = 0;

    private int presentismo = 0;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaDesde = null;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaHasta = null;

    private String situacion = null;
}
