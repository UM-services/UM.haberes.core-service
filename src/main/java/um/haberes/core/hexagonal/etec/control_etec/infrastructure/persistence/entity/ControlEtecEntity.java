package um.haberes.core.hexagonal.etec.control_etec.infrastructure.persistence.entity;

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
@Table(name = "control_etec", uniqueConstraints = {@UniqueConstraint(columnNames = {"anho", "mes"})})
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ControlEtecEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long controlEtecId = null;

    private Integer anho = 0;

    private Integer mes = 0;

    private BigDecimal valorPuntoEtec = BigDecimal.ZERO;

    private BigDecimal montoFijoCargo = BigDecimal.ZERO;

    private BigDecimal horasReferenciaCargo = BigDecimal.ZERO;

    private BigDecimal basicoMaestroGrado = BigDecimal.ZERO;

    private Integer codigoAdicionalDirectivo = null;

    private BigDecimal porcentajePresentismo = BigDecimal.ZERO;

    private BigDecimal porcentajeAdicionalCarrera = BigDecimal.ZERO;

    private BigDecimal garantia = BigDecimal.ZERO;

    private BigDecimal horasAdicionalCargaHoraria = BigDecimal.ZERO;

    private BigDecimal valorHoraAdicionalCargaHoraria = BigDecimal.ZERO;
}
