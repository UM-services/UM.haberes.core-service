package um.haberes.core.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "designacion")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DesignacionEntity extends AuditableEntity {

    @Id
    private Integer categoriaId = null;

    @Setter(AccessLevel.NONE)
    private Integer designacionTipoId = null;

    @Setter(AccessLevel.NONE)
    private Integer cargoTipoId = null;

    @Setter(AccessLevel.NONE)
    private Byte anual = 0;

    @Setter(AccessLevel.NONE)
    private Byte semestral = 0;

    @Setter(AccessLevel.NONE)
    private Byte aCargo = 0;
}
