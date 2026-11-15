package um.haberes.core.hexagonal.etec.arraigo_escala.infrastructure.persistence.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "etec_arraigo_escala",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"aniosMinimos"})})
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EscalaArraigoEtecEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long etecArraigoEscalaId = null;

    private Integer aniosMinimos = 0;

    private BigDecimal porcentaje = BigDecimal.ZERO;
}
