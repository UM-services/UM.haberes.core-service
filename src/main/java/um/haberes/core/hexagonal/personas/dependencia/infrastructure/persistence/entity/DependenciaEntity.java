package um.haberes.core.hexagonal.personas.dependencia.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import um.haberes.core.hexagonal.facultad.infrastructure.persistence.entity.FacultadEntity;
import um.haberes.core.hexagonal.geografica.infrastructure.persistence.entity.GeograficaEntity;
import um.haberes.core.model.AuditableEntity;

@Entity
@Table(name = "dependencia")
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DependenciaEntity extends AuditableEntity {

    @Id
    private Integer dependenciaId;

    @Builder.Default
    private String nombre = "";

    @Builder.Default
    private String acronimo = "";

    private Integer facultadId;

    private Integer geograficaId;

    @OneToOne(optional = false)
    @JoinColumn(name = "facultadId", insertable = false, updatable = false)
    private FacultadEntity facultad;

    @OneToOne(optional = false)
    @JoinColumn(name = "geograficaId", insertable = false, updatable = false)
    private GeograficaEntity geografica;

    public String getSedeKey() {
        return String.valueOf(facultadId) + "." + geograficaId;
    }
}
