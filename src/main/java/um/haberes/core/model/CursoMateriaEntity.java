package um.haberes.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import um.haberes.core.hexagonal.cursos.curso.infrastructure.persistence.entity.CursoEntity;
import um.haberes.core.hexagonal.facultad.infrastructure.persistence.entity.FacultadEntity;
import um.haberes.core.hexagonal.geografica.infrastructure.persistence.entity.GeograficaEntity;

import java.math.BigDecimal;

@Entity
@Table(name = "curso_materia", uniqueConstraints = {@UniqueConstraint(columnNames = {"cursoId", "facultadId", "geograficaId", "planId", "carreraId", "materiaId"})})
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CursoMateriaEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cursoMateriaId = null;

    private Long cursoId = null;

    private Integer facultadId = null;

    private Integer geograficaId = null;

    private Integer planId = null;

    private Integer carreraId = null;

    private String materiaId = null;

    private BigDecimal proporcion = BigDecimal.ZERO;

    @OneToOne
    @JoinColumn(name = "cursoId", insertable = false, updatable = false)
    private CursoEntity curso = null;

    @OneToOne
    @JoinColumn(name = "facultadId", insertable = false, updatable = false)
    private FacultadEntity facultad = null;

    @OneToOne
    @JoinColumn(name = "geograficaId", insertable = false, updatable = false)
    private GeograficaEntity geografica = null;
}
