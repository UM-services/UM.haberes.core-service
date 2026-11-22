package um.haberes.core.hexagonal.etec.presentismo.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class EtecPresentismoRequest {

    @NotNull
    private Long asignacionCargoId;

    @NotNull
    private Integer anho;

    @NotNull
    private Integer mes;

    @NotBlank
    @Size(max = 255)
    private String motivo;
}
