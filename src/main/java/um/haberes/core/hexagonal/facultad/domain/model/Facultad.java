package um.haberes.core.hexagonal.facultad.domain.model;

import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Facultad {
    private Integer facultadId;
    private String nombre;
    private String reducido;
    private String server;
    private String backendServer;
    private Integer backendPort;
    private String dbName;
    private String dsn;
}
