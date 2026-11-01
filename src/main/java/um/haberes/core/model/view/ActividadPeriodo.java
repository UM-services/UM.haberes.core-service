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

@Entity
@Table(name = "vw_actividad_periodo")
@Immutable
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ActividadPeriodo extends AuditableEntity {

    @Id
    private Long actividadId = null;

    private Long legajoId = null;

    private Integer anho = null;

    private Integer mes = null;

    private Byte docente = null;

    private Byte otras = null;

    private Byte clases = null;

    private Integer dependenciaId = null;

    private Long periodo = null;
}
