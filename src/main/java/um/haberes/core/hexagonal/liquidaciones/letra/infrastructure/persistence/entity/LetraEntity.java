package um.haberes.core.hexagonal.liquidaciones.letra.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import um.haberes.core.model.AuditableEntity;

import java.math.BigDecimal;

@Entity
@Table(name = "letra", uniqueConstraints = {@UniqueConstraint(columnNames = {"legajoId", "anho", "mes"})})
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LetraEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long letraId = null;

    private Long legajoId = null;

    private int anho = 0;

    private int mes = 0;

    private BigDecimal neto = BigDecimal.ZERO;

    private String cadena = "";
}
