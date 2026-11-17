package um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.infrastructure.web.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
public class ArchivoAcreditacionRequest {

    @NotNull
    private Integer anho;

    @NotNull
    @Min(1)
    @Max(12)
    private Integer mes;

    @NotNull
    private LocalDate fechaPago;

    @Builder.Default
    private Integer numEnvio = 1;

    @Builder.Default
    private String salida = "T";

    @Builder.Default
    private Integer dependenciaId = 0;

    @Builder.Default
    private Integer codigoId = 0;

    @Builder.Default
    private Boolean cci = false;

    @Builder.Default
    private Boolean individual = false;

    @Builder.Default
    @Valid
    private List<LegajoAcreditarRequest> legajos = new ArrayList<>();
}
