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
import um.haberes.core.hexagonal.cursos.cargo_tipo.infrastructure.persistence.entity.CargoTipoEntity;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.persistence.entity.CategoriaEntity;
import um.haberes.core.hexagonal.cursos.curso.infrastructure.persistence.entity.CursoEntity;
import um.haberes.core.hexagonal.cursos.designacion_tipo.infrastructure.persistence.entity.DesignacionTipoEntity;
import um.haberes.core.hexagonal.personas.persona.infrastructure.persistence.entity.PersonaEntity;

import java.math.BigDecimal;

@Entity
@Table(name = "vw_curso_cargo_tipo")
@Immutable
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CursoCargoTipo extends AuditableEntity {

    @Id
    private Long cursoCargoId = null;

    private Long cursoId = null;

    private Integer anho = null;

    private Integer mes = null;

    private Integer cargoTipoId = null;

    private Long legajoId = null;

    private BigDecimal horasSemanales = null;

    private BigDecimal horasTotales = null;

    private Integer designacionTipoId = null;

    private Integer categoriaId = null;

    private Byte desarraigo = null;

    private Long cursoCargoNovedadId = null;

    private Byte aCargo = null;

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
