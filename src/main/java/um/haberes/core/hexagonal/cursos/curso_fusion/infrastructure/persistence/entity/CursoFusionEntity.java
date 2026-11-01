package um.haberes.core.hexagonal.cursos.curso_fusion.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import um.haberes.core.hexagonal.facultad.infrastructure.persistence.entity.FacultadEntity;
import um.haberes.core.hexagonal.geografica.infrastructure.persistence.entity.GeograficaEntity;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.persistence.entity.CategoriaEntity;
import um.haberes.core.hexagonal.personas.persona.infrastructure.persistence.entity.PersonaEntity;
import um.haberes.core.model.AuditableEntity;
import um.haberes.core.hexagonal.cursos.cargo_tipo.infrastructure.persistence.entity.CargoTipoEntity;

@Entity
@Table(name = "curso_fusion")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CursoFusionEntity extends AuditableEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cursoFusionId = null;

    private Long legajoId = null;

    private int anho = 0;

    private int mes = 0;

    private Integer facultadId = null;

    private Integer geograficaId = null;

    private Integer cargoTipoId = null;

    private Integer designacionTipoId = null;

    private Byte anual = 0;

    private Integer categoriaId = null;

    @OneToOne(optional = true)
    @JoinColumn(name = "legajoId", insertable = false, updatable = false)
    private PersonaEntity persona = null;

    @OneToOne(optional = true)
    @JoinColumn(name = "facultadId", insertable = false, updatable = false)
    private FacultadEntity facultad = null;

    @OneToOne(optional = true)
    @JoinColumn(name = "geograficaId", insertable = false, updatable = false)
    private GeograficaEntity geografica = null;

    @OneToOne(optional = true)
    @JoinColumn(name = "cargoTipoId", insertable = false, updatable = false)
    private CargoTipoEntity cargoTipo = null;

    @OneToOne(optional = true)
    @JoinColumn(name = "designacionTipoId", insertable = false, updatable = false)
    private CargoTipoEntity designacionTipo = null;

    @OneToOne(optional = true)
    @JoinColumn(name = "categoriaId", insertable = false, updatable = false)
    private CategoriaEntity categoria = null;
}
