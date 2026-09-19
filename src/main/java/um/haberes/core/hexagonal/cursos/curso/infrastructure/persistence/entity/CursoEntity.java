package um.haberes.core.hexagonal.cursos.curso.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import um.haberes.core.hexagonal.facultad.infrastructure.persistence.entity.FacultadEntity;
import um.haberes.core.hexagonal.geografica.infrastructure.persistence.entity.GeograficaEntity;
import um.haberes.core.model.AuditableEntity;
import um.haberes.core.model.NivelEntity;

@Entity
@Table(name = "curso")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CursoEntity extends AuditableEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cursoId = null;

    private String nombre = "";

    private Integer facultadId = null;

    private Integer geograficaId = null;

    private Byte anual = 0;

    private Byte semestre1 = 0;

    private Byte semestre2 = 0;

    private Integer nivelId = null;

    private Byte adicionalCargaHoraria = 0;

    @OneToOne
    @JoinColumn(name = "facultadId", insertable = false, updatable = false)
    private FacultadEntity facultad = null;

    @OneToOne
    @JoinColumn(name = "geograficaId", insertable = false, updatable = false)
    private GeograficaEntity geografica = null;

    @OneToOne
    @JoinColumn(name = "nivelId", insertable = false, updatable = false)
    private NivelEntity nivel = null;
}
