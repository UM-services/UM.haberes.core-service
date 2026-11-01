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
        name = "vw_imputado_aporte",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"anho", "mes", "cuentaAportes"})}
)
@Immutable
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ImputadoAporte {

    @Id
    private String uniqueId = null;

    private Integer anho = null;

    private Integer mes = null;

    private BigDecimal cuentaAportes = null;

    private BigDecimal porcentajeImputado = null;

    private BigDecimal totalImputado = null;
}
