package um.haberes.core.model.view;

import jakarta.persistence.*;
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
@Table(name = "vw_cargo_clase_detalle_periodo")
@Immutable
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CargoClaseDetallePeriodo extends AuditableEntity {

    @Id
    private Long cargoClaseDetalleId = null;

    private Long legajoId = null;

    private Integer anho = null;

    private Integer mes = null;

    private Long cargoClaseId = null;

    private Integer dependenciaId = null;

    private Integer facultadId = null;

    private Integer geograficaId = null;

    private Integer horas = null;

    private BigDecimal valorHora = null;

    private Long cargoClasePeriodoId = null;

    private Byte liquidado = null;

    private Long periodo = null;
}
