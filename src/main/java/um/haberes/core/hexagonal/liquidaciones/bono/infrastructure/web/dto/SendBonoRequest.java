package um.haberes.core.hexagonal.liquidaciones.bono.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
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
public class SendBonoRequest {

    @NotBlank
    private String mailInstitucional;

    private Long legajoIdSolicitud;
}
