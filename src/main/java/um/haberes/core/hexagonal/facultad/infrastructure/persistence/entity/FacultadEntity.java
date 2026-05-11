package um.haberes.core.hexagonal.facultad.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import um.haberes.core.kotlin.model.Auditable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "facultad")
@EqualsAndHashCode(callSuper = true)
@Builder
public class FacultadEntity extends Auditable {

    @Id
    private Integer facultadId;

    private String nombre;
    private String reducido;
    private String server;
    private String backendServer;
    private Integer backendPort;
    private String dbName;
    private String dsn;

}
