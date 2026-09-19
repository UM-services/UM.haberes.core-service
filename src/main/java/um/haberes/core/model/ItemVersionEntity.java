package um.haberes.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "item_version")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ItemVersionEntity extends AuditableEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemVersionId = null;

    private Long legajoId = null;

    private int anho = 0;

    private int mes = 0;

    private Integer codigoId = null;

    private String codigoNombre = "";

    private BigDecimal importe = BigDecimal.ZERO;
}
