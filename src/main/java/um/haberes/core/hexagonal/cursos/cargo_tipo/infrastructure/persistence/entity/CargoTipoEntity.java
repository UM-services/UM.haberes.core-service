package um.haberes.core.hexagonal.cursos.cargo_tipo.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import um.haberes.core.model.AuditableEntity;

@Entity
@Table(name = "cargo_tipo")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CargoTipoEntity extends AuditableEntity {

    @Id
    private Integer cargoTipoId = null;

    private Byte aCargo = 0;

    private String nombre = "";

    private int precedencia = 0;
}
