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

@Entity
@Table(name = "vw_afip_concepto_sueldo_search")
@Immutable
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AfipConceptoSueldoSearch {

    @Id
    private Long afipConceptoSueldoId = null;

    private String descripcion = "";

    private Byte asignado = 0;

    private String search = "";
}
