package um.haberes.core.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "afip_concepto_sueldo")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AfipConceptoSueldoEntity extends AuditableEntity {

    @Id
    private Long afipConceptoSueldoId = null;

    private String descripcion = "";

    private Byte asignado = 0;
}
