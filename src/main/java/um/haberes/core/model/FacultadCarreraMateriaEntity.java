package um.haberes.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "facultad_carrera_materia", uniqueConstraints = {@UniqueConstraint(columnNames = {"facultadId", "planId", "carreraId", "materiaId"})})
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FacultadCarreraMateriaEntity extends AuditableEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long facultadCarreraMateriaId = null;

    private Integer facultadId = null;

    private Integer planId = null;

    private Integer carreraId = null;

    private String materiaId = null;

    @OneToOne
    @JoinColumns({
        @JoinColumn(
            name = "facultadId",
            referencedColumnName = "facultadId",
            insertable = false,
            updatable = false
        ),
        @JoinColumn(name = "planId", referencedColumnName = "planId", insertable = false, updatable = false),
        @JoinColumn(name = "carreraId", referencedColumnName = "carreraId", insertable = false, updatable = false)
    })
    private FacultadCarreraEntity facultadCarrera = null;

    @OneToOne
    @JoinColumns({
        @JoinColumn(
            name = "facultadId",
            referencedColumnName = "facultadId",
            insertable = false,
            updatable = false
        ),
        @JoinColumn(name = "planId", referencedColumnName = "planId", insertable = false, updatable = false),
        @JoinColumn(name = "materiaId", referencedColumnName = "materiaId", insertable = false, updatable = false)
    })
    private FacultadMateriaEntity facultadMateria = null;
}
