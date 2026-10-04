package um.haberes.core.model.view;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;

@Entity
@Table(name = "vw_total_salida")
@Immutable
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TotalSalida {

    @Id
    private String uniqueId = null;

    private Integer anho = null;

    private Integer mes = null;

    private BigDecimal totalRemunerativo = BigDecimal.ZERO;

    private BigDecimal totalNoRemunerativo = BigDecimal.ZERO;

    private BigDecimal totalDeduccion = BigDecimal.ZERO;

    private BigDecimal totalNeto = BigDecimal.ZERO;
}
