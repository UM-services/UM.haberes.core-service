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
        name = "vw_asignado_categoria",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"legajoId", "dependenciaId", "categoriaId"})}
)
@Immutable
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AsignadoCategoria {

    @Id
    private String uniqueId = null;

    private Long legajoId = null;

    private Integer dependenciaId = null;

    private Integer categoriaId = null;

    private Long periodoDesde = null;

    private Long periodoHasta = null;

    private BigDecimal basicoDesde = BigDecimal.ZERO;

    private BigDecimal basicoHasta = BigDecimal.ZERO;
}
