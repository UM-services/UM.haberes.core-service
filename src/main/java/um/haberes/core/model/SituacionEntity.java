package um.haberes.core.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "situacion")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SituacionEntity extends AuditableEntity {

    @Id
    private Integer situacionId = null;

    private String nombre = "";

    private Byte interino = 0;

    private Byte ordinario = 0;

    private Byte planta = 0;

    private Byte contratado = 0;

    private Byte secundario = 0;
}
