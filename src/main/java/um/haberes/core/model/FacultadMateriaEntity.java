package um.haberes.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "facultad_materia", uniqueConstraints = {@UniqueConstraint(columnNames = {"facultadId", "planId", "materiaId"})})
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FacultadMateriaEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long facultadMateriaId = null;

    private Integer facultadId = null;

    private Integer planId = null;

    private String materiaId = null;

    private String nombre = "";
}
