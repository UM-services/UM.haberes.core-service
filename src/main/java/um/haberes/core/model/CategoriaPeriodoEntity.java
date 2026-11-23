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
@Table(name = "categoria_periodo", uniqueConstraints = {@UniqueConstraint(columnNames = {"categoriaId", "anho", "mes"})})
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaPeriodoEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoriaPeriodoId = null;

    private Integer categoriaId = null;

    private int anho = 0;

    private int mes = 0;

    private String nombre = "";

    private BigDecimal basico = BigDecimal.ZERO;

    private Byte docente = 0;

    private Byte noDocente = 0;

    private Byte liquidaPorHora = 0;

    private BigDecimal estadoDocente = BigDecimal.ZERO;

    /** Puntaje del cargo ETEC vigente en el período. */
    private BigDecimal puntos = BigDecimal.ZERO;

    /** 1 = la categoría liquida las horas de clase ETEC en el período. */
    private Byte etecHoras = 0;
}
