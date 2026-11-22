package um.haberes.core.hexagonal.etec.presentismo.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import um.haberes.core.hexagonal.etec.asignacion_cargo.infrastructure.persistence.entity.EtecAsignacionCargoEntity;
import um.haberes.core.model.AuditableEntity;

@Entity
@Table(name = "etec_presentismo",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_etec_presentismo_asignacion_periodo",
                columnNames = {"asignacionCargoId", "anho", "mes"}))
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EtecPresentismoEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long presentismoId = null;

    private Long asignacionCargoId = null;

    private Integer anho = null;

    private Integer mes = null;

    private String motivo = null;

    @OneToOne
    @JoinColumn(name = "asignacionCargoId", insertable = false, updatable = false)
    private EtecAsignacionCargoEntity asignacion = null;
}
