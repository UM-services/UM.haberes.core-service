package um.haberes.core.hexagonal.geografica.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import um.haberes.core.model.AuditableEntity;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "geografica")
@EqualsAndHashCode(callSuper = true)
@Builder
public class GeograficaEntity extends AuditableEntity {

    @Id
    private Integer geograficaId;

    private String nombre;
    private String reducido;
    private BigDecimal desarraigo;
    private Integer geograficaIdReemplazo;

}
