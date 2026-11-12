package um.haberes.core.hexagonal.etec.asignacion_cargo.infrastructure.web.dto;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class EtecAsignacionCargoResponse {

    private Long asignacionCargoId;

    private Long legajoId;

    private Integer categoriaId;

    private Integer dependenciaId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaAlta;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaBaja;

    private String observaciones;

    private String categoriaNombre;

    private String dependenciaNombre;

    private String personaApellido;

    private String personaNombre;
}
