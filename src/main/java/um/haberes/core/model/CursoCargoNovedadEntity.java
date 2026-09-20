package um.haberes.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import um.haberes.core.hexagonal.cursos.cargo_tipo.infrastructure.persistence.entity.CargoTipoEntity;
import um.haberes.core.hexagonal.cursos.curso.infrastructure.persistence.entity.CursoEntity;
import um.haberes.core.hexagonal.personas.persona.infrastructure.persistence.entity.PersonaEntity;
import um.haberes.core.util.Jsonifyable;

import java.math.BigDecimal;

@Entity
@Table(name = "curso_cargo_novedad", uniqueConstraints = {@UniqueConstraint(columnNames = {"cursoId", "anho", "mes", "cargoTipoId", "legajoId"})})
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CursoCargoNovedadEntity extends AuditableEntity implements Jsonifyable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToOne
    @JoinColumn(name = "cursoId", insertable = false, updatable = false)
    private CursoEntity curso = null;

    @OneToOne
    @JoinColumn(name = "cargoTipoId", insertable = false, updatable = false)
    private CargoTipoEntity cargoTipo = null;

    @OneToOne
    @JoinColumn(name = "legajoId", insertable = false, updatable = false)
    private PersonaEntity persona = null;
}
