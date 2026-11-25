package um.haberes.core.hexagonal.seguridad.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import um.haberes.core.model.AuditableEntity;

@Getter
@Setter
@Entity
@Table(name = "rol_permiso", uniqueConstraints = {
        @UniqueConstraint(name = "uq_rol_permiso", columnNames = { "rolId", "permisoId" })
})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolPermisoEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rolPermisoId;

    private Long rolId;
    private Long permisoId;
}
