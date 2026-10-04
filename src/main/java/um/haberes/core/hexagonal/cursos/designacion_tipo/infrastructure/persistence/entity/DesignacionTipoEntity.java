package um.haberes.core.hexagonal.cursos.designacion_tipo.infrastructure.persistence.entity;

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
@Table(name = "designacion_tipo")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DesignacionTipoEntity extends AuditableEntity {

    @Id
    private int designacionTipoId = 0;

    private String nombre = "";

    private BigDecimal horasSemanales = BigDecimal.ZERO;

    private BigDecimal horasTotales = BigDecimal.ZERO;

    private int simples = 0;
}
