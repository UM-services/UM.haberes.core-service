package um.haberes.core.hexagonal.seguridad.infrastructure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermisoEfectivoResponse {

    private Long legajoId;
    private List<String> permisos;
    private boolean administrador;
}
