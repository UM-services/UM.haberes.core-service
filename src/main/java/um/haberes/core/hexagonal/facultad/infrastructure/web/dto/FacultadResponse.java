package um.haberes.core.hexagonal.facultad.infrastructure.web.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacultadResponse {
    private Integer facultadId;
    private String nombre;
    private String reducido;
    private String server;
    private String backendServer;
    private Integer backendPort;
    private String dbName;
    private String dsn;
}
