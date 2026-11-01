package um.haberes.core.model.view;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;

@Entity
@Table(
        name = "vw_novedad_acumulado",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"legajoId", "anho", "mes", "codigoId"})}
)
@Immutable
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NovedadAcumulado {

    @Id
    private String unified = null;

    private Long legajoId = null;

    private Integer anho = null;

    private Integer mes = null;

    private Integer codigoId = null;

    private BigDecimal importe = null;

    private Integer cantidad = null;
}
