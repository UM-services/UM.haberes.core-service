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

@Entity
@Table(
        name = "vw_codigo_periodo",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"anho", "mes", "codigoId"})}
)
@Immutable
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CodigoPeriodo {

    @Id
    private Integer codigoId = null;

    private Integer anho = null;

    private Integer mes = null;
}
