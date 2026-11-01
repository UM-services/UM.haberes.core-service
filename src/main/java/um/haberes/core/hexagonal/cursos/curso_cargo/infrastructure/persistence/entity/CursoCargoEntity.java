package um.haberes.core.hexagonal.cursos.curso_cargo.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.persistence.entity.CategoriaEntity;
import um.haberes.core.hexagonal.personas.persona.infrastructure.persistence.entity.PersonaEntity;
import um.haberes.core.model.AuditableEntity;
import um.haberes.core.hexagonal.cursos.cargo_tipo.infrastructure.persistence.entity.CargoTipoEntity;
import um.haberes.core.hexagonal.cursos.curso.infrastructure.persistence.entity.CursoEntity;
import um.haberes.core.hexagonal.cursos.designacion_tipo.infrastructure.persistence.entity.DesignacionTipoEntity;

import java.math.BigDecimal;

@Entity
@Table(name = "curso_cargo", uniqueConstraints = {@UniqueConstraint(columnNames = {"cursoId", "anho", "mes", "cargoTipoId", "legajoId"})})
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CursoCargoEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cursoCargoId = null;

    private Long cursoId = null;

    private int anho = 0;

    private int mes = 0;

    private Integer cargoTipoId = null;

    private Long legajoId = null;

    private BigDecimal horasSemanales = BigDecimal.ZERO;

    private BigDecimal horasTotales = BigDecimal.ZERO;

    private Integer designacionTipoId = null;

    private Integer categoriaId = null;

    private Byte desarraigo = 0;

    private Long cursoCargoNovedadId = null;

    @OneToOne
    @JoinColumn(name = "cursoId", insertable = false, updatable = false)
    private CursoEntity curso = null;

    @OneToOne
    @JoinColumn(name = "cargoTipoId", insertable = false, updatable = false)
    private CargoTipoEntity cargoTipo = null;

    @OneToOne
    @JoinColumn(name = "legajoId", insertable = false, updatable = false)
    private PersonaEntity persona = null;

    @OneToOne
    @JoinColumn(name = "designacionTipoId", insertable = false, updatable = false)
    private DesignacionTipoEntity designacionTipo = null;

    @OneToOne
    @JoinColumn(name = "categoriaId", insertable = false, updatable = false)
    private CategoriaEntity categoria = null;
}
