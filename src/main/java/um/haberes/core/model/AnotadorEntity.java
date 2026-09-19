package um.haberes.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import um.haberes.core.hexagonal.facultad.infrastructure.persistence.entity.FacultadEntity;
import um.haberes.core.hexagonal.personas.persona.infrastructure.persistence.entity.PersonaEntity;

@Entity
@Table(name = "anotador")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AnotadorEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long anotadorId = null;

    private Long legajoId = null;

    private int anho = 0;

    private int mes = 0;

    private Integer facultadId = null;

    private String anotacion = null;

    private Byte visado = 0;

    private String ipVisado = null;

    @Column(name = "`user`")
    private String user = null;

    private String respuesta = null;

    private Byte autorizado = 0;

    private Byte rechazado = 0;

    private Byte rectorado = 0;

    private Byte transferido = 0;

    @OneToOne
    @JoinColumn(name = "legajoId", insertable = false, updatable = false)
    private PersonaEntity persona = null;

    @OneToOne
    @JoinColumn(name = "facultadId", insertable = false, updatable = false)
    private FacultadEntity facultad = null;
}
