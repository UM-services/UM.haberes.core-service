package um.haberes.core.hexagonal.personas.persona_facultad.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import um.haberes.core.hexagonal.facultad.infrastructure.persistence.entity.FacultadEntity;
import um.haberes.core.hexagonal.personas.persona.infrastructure.persistence.entity.PersonaEntity;
import um.haberes.core.model.AuditableEntity;

@Entity
@Table(name = "persona_facultad", uniqueConstraints = {@UniqueConstraint(columnNames = {"legajoId", "facultadId"})})
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PersonaFacultadEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personaFacultadId;

    private Long legajoId;

    private Integer facultadId;

    @OneToOne
    @JoinColumn(name = "legajoId", insertable = false, updatable = false)
    private PersonaEntity persona;

    @OneToOne
    @JoinColumn(name = "facultadId", insertable = false, updatable = false)
    private FacultadEntity facultad;
}
