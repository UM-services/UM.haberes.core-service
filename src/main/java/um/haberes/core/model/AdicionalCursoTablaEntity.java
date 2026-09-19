package um.haberes.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import um.haberes.core.hexagonal.facultad.infrastructure.persistence.entity.FacultadEntity;
import um.haberes.core.hexagonal.geografica.infrastructure.persistence.entity.GeograficaEntity;

import java.util.List;

@Entity
@Table(name = "adicional_curso_tabla")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AdicionalCursoTablaEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adicionalCursoTablaId = null;

    private long periodoDesde = 0L;

    private long periodoHasta = 0L;

    private Integer facultadId = null;

    private Integer geograficaId = null;

    @OneToOne
    @JoinColumn(name = "facultadId", insertable = false, updatable = false)
    private FacultadEntity facultad = null;

    @OneToOne
    @JoinColumn(name = "geograficaId", insertable = false, updatable = false)
    private GeograficaEntity geografica = null;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "adicionalCursoTablaId", insertable = false, updatable = false)
    private List<AdicionalCursoRangoEntity> adicionalCursoRangos = null;
}
