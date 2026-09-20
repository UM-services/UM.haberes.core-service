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
import um.haberes.core.model.AuditableEntity;

import java.math.BigDecimal;

@Entity
@Table(name = "vw_categoria_search")
@Immutable
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaSearch extends AuditableEntity {

    @Id
    private Integer categoriaId = null;

    private String nombre = null;

    private BigDecimal basico = null;

    private String search = null;
}
