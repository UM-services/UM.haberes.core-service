package um.haberes.core.hexagonal.seguridad.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import um.haberes.core.model.AuditableEntity;

@Getter
@Setter
@Entity
@Table(name = "rol")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rolId;

    private String nombre;
    private String descripcion;

    @Builder.Default
    private String aplicacion = "HABERES";

    @Builder.Default
    private Byte activo = 1;
}
