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
@Table(name = "permiso")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermisoEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long permisoId;

    private String clave;
    private String descripcion;
    private String modulo;

    @Builder.Default
    private String aplicacion = "HABERES";

    @Builder.Default
    private Byte activo = 1;
}
