package um.haberes.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "legajo_inasistencia", uniqueConstraints = {@UniqueConstraint(columnNames = {"legajoId", "anho", "mes", "facultadId", "geograficaId"})})
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LegajoInasistenciaEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long legajoInasistenciaId = null;

    private Long legajoId = null;

    private int anho = 0;

    private int mes = 0;

    private Integer facultadId = null;

    private Integer geograficaId = null;

    private int cantidadInasistencias = 0;
}
