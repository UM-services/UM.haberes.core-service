package um.haberes.core.hexagonal.etec.hora_escala.infrastructure.persistence.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import um.haberes.core.model.AuditableEntity;

@Entity
@Table(name = "etec_hora_escala",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"controlEtecId", "horasDesde"})},
        indexes = {@Index(name = "idx_etec_hora_escala_control", columnList = "controlEtecId")})
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EscalaHoraEtecEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long etecHoraEscalaId = null;

    private Long controlEtecId;

    private BigDecimal horasDesde = BigDecimal.ZERO;

    private BigDecimal horasHasta = BigDecimal.ZERO;

    private BigDecimal montoFijo = BigDecimal.ZERO;

    private BigDecimal valorHora = BigDecimal.ZERO;
}
