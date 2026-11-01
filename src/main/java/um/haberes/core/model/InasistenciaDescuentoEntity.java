package um.haberes.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "inasistencia_descuento", uniqueConstraints = {@UniqueConstraint(columnNames = {"facultadId", "geograficaId", "desde"})})
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InasistenciaDescuentoEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inasistenciaDescuentoId = null;

    private Integer facultadId = null;

    private Integer geograficaId = null;

    private int desde = 0;

    private int hasta = 0;

    private int porcentaje = 0;
}
