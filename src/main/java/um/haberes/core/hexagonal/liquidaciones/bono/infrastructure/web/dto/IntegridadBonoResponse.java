package um.haberes.core.hexagonal.liquidaciones.bono.infrastructure.web.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntegridadBonoResponse {

    private Long legajoId;

    private Integer anho;

    private Integer mes;

    private boolean ok;

    @Builder.Default
    private List<String> faltantes = new ArrayList<>();
}
