package um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import um.haberes.core.model.AuditableEntity;

import java.math.BigDecimal;

@Entity
@Table(name = "categoria")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaEntity extends AuditableEntity {

    @Id
    private Integer categoriaId = null;

    private String nombre = "";

    private BigDecimal basico = BigDecimal.ZERO;

    private Byte docente = 0;

    private Byte noDocente = 0;

    private Byte liquidaPorHora = 0;

    private BigDecimal estadoDocente = BigDecimal.ZERO;
}
