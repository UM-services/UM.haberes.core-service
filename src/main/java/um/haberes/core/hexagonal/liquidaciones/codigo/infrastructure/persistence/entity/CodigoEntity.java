package um.haberes.core.hexagonal.liquidaciones.codigo.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import um.haberes.core.model.AfipConceptoSueldoEntity;
import um.haberes.core.model.AuditableEntity;

@Entity
@Table(name = "codigo")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CodigoEntity extends AuditableEntity {

    @Id
    private Integer codigoId = null;

    private String nombre = "";

    private Byte docente = 0;

    private Byte noDocente = 0;

    private Byte transferible = 0;

    private Byte incluidoEtec = 0;

    private Long afipConceptoSueldoIdPrimerSemestre = null;

    private Long afipConceptoSueldoIdSegundoSemestre = null;

    @OneToOne
    @JoinColumn(name = "afipConceptoSueldoIdPrimerSemestre", insertable = false, updatable = false)
    private AfipConceptoSueldoEntity afipConceptoSueldoPrimerSemestre = null;

    @OneToOne
    @JoinColumn(name = "afipConceptoSueldoIdSegundoSemestre", insertable = false, updatable = false)
    private AfipConceptoSueldoEntity afipConceptoSueldoSegundoSemestre = null;
}
