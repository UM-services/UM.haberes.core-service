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
@Table(name = "adicional_curso_rango")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AdicionalCursoRangoEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adicionalCursoRangoId = null;

    private int horasDesde = 0;

    private int horasHasta = 0;

    private BigDecimal porcentaje = BigDecimal.ZERO;

    private Long adicionalCursoTablaId = null;
}
