package um.haberes.core.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import um.haberes.core.hexagonal.liquidaciones.codigo.infrastructure.persistence.entity.CodigoEntity;

@Entity
@Table(name = "codigo_grupo")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CodigoGrupoEntity extends AuditableEntity {

    @Id
    private Integer codigoId = null;

    private Byte remunerativo = 0;

    private Byte noRemunerativo = 0;

    private Byte deduccion = 0;

    private Byte total = 0;

    @OneToOne
    @JoinColumn(name = "codigoId", insertable = false, updatable = false)
    private CodigoEntity codigo = null;
}
