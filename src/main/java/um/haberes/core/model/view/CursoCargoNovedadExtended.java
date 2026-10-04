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
@Table(
        name = "vw_curso_cargo_novedad_extended",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"cursoId", "anho", "mes", "cargoTipoId", "legajoId"})}
)
@Immutable
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CursoCargoNovedadExtended extends AuditableEntity {

    @Id
    private Long cursoCargoNovedadId = null;

    private Long cursoId = null;

    private int anho = 0;

    private int mes = 0;

    private Integer cargoTipoId = null;

    private Long legajoId = null;

    private BigDecimal horasSemanales = BigDecimal.ZERO;

    private BigDecimal horasTotales = BigDecimal.ZERO;

    private Byte desarraigo = 0;

    private Byte alta = 0;

    private Byte baja = 0;

    private Byte cambio = 0;

    private String solicitud = null;

    private Byte autorizado = 0;

    private Byte rechazado = 0;

    private String respuesta = null;

    private Byte transferido = 0;

    private Byte aCargo = 0;

    private String apellido = "";

    private String nombre = "";
}
