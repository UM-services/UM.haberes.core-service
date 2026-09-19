package um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import um.haberes.core.model.AuditableEntity;

import java.math.BigDecimal;

@Entity
@Table(name = "legajo_categoria_imputacion")
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LegajoCategoriaImputacionEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long legajoCategoriaImputacionId;

    private Long legajoId;

    private int anho;

    private int mes;

    private Integer dependenciaId;

    private Integer facultadId;

    private Integer geograficaId;

    private Integer categoriaId;

    private BigDecimal cuentaSueldos;

    @Builder.Default
    private BigDecimal basico = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal antiguedad = BigDecimal.ZERO;

    private BigDecimal cuentaAportes;
}
