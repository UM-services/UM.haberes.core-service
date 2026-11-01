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
@Table(name = "control", uniqueConstraints = {@UniqueConstraint(columnNames = {"anho", "mes"})})
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ControlEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long controlId = null;

    private Integer anho = null;

    private Integer mes = null;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaDesde = null;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaHasta = null;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaPago = null;

    private String aporteJubilatorio = null;

    private String depositoBanco = null;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaDeposito = null;

    private BigDecimal doctorado = BigDecimal.ZERO;

    private BigDecimal maestria = BigDecimal.ZERO;

    private BigDecimal especializacion = BigDecimal.ZERO;

    private BigDecimal familiaNumerosa = BigDecimal.ZERO;

    private BigDecimal escuelaPrimaria = BigDecimal.ZERO;

    private BigDecimal escuelaSecundaria = BigDecimal.ZERO;

    private BigDecimal escuelaPrimariaNumerosa = BigDecimal.ZERO;

    private BigDecimal escuelaSecundariaNumerosa = BigDecimal.ZERO;

    private BigDecimal prenatal = BigDecimal.ZERO;

    private BigDecimal libre = BigDecimal.ZERO;

    private BigDecimal ayudaEscolar = BigDecimal.ZERO;

    private BigDecimal matrimonio = BigDecimal.ZERO;

    private BigDecimal nacimiento = BigDecimal.ZERO;

    private BigDecimal funcionDireccion = BigDecimal.ZERO;

    private BigDecimal mayorResponsabilidadPatrimonial = BigDecimal.ZERO;

    private BigDecimal polimedb = BigDecimal.ZERO;

    private BigDecimal polimedo = BigDecimal.ZERO;

    private BigDecimal montoeci = BigDecimal.ZERO;

    private BigDecimal valampo = BigDecimal.ZERO;

    private BigDecimal jubilaem = BigDecimal.ZERO;

    private BigDecimal inssjpem = BigDecimal.ZERO;

    private BigDecimal osociaem = BigDecimal.ZERO;

    private BigDecimal jubilpat = BigDecimal.ZERO;

    private BigDecimal inssjpat = BigDecimal.ZERO;

    private BigDecimal osocipat = BigDecimal.ZERO;

    private BigDecimal ansalpat = BigDecimal.ZERO;

    private BigDecimal salfapat = BigDecimal.ZERO;

    private BigDecimal minimoAporte = BigDecimal.ZERO;

    private BigDecimal maximoAporte = BigDecimal.ZERO;

    private BigDecimal mincontr = BigDecimal.ZERO;

    private BigDecimal maximo1sijp = BigDecimal.ZERO;

    private BigDecimal maximo2sijp = BigDecimal.ZERO;

    private BigDecimal maximo3sijp = BigDecimal.ZERO;

    private BigDecimal maximo4sijp = BigDecimal.ZERO;

    private BigDecimal maximo5sijp = BigDecimal.ZERO;

    private BigDecimal estadoDocenteTitular = BigDecimal.ZERO;

    private BigDecimal estadoDocenteAdjunto = BigDecimal.ZERO;

    private BigDecimal estadoDocenteAuxiliar = BigDecimal.ZERO;

    private BigDecimal adicionalHoraCargoClase = BigDecimal.ZERO;

    private BigDecimal horaReferenciaEtec = BigDecimal.ZERO;

    private Integer modoLiquidacionId = null;

    @OneToOne
    @JoinColumn(name = "modoLiquidacionId", insertable = false, updatable = false)
    private ModoLiquidacionEntity modoLiquidacion = null;
}
