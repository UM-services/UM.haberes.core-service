package um.haberes.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "facultad_carrera", uniqueConstraints = {@UniqueConstraint(columnNames = {"facultadId", "planId", "carreraId"})})
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FacultadCarreraEntity extends AuditableEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long facultadCarreraId = null;

    private Integer facultadId = null;

    private Integer planId = null;

    private Integer carreraId = null;

    private String nombre = "";
}
