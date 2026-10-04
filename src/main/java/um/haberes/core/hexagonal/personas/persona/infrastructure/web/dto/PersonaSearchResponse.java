package um.haberes.core.hexagonal.personas.persona.infrastructure.web.dto;

import java.math.BigDecimal;
import java.text.MessageFormat;
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
public class PersonaSearchResponse {

    private Long legajoId;

    private BigDecimal documento;

    private String apellido;

    private String nombre;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime nacimiento;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime altaDocente;

    private Integer ajusteDocente;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime altaAdministrativa;

    private Integer ajusteAdministrativo;

    private String estadoCivil;

    private Integer situacionId;

    private Byte reemplazoDesarraigo;

    private Byte mitadDesarraigo;

    private String cuil;

    private Integer posgrado;

    private Integer estado;

    private String liquida;

    private Integer estadoAfip;

    private Integer dependenciaId;

    private String salida;

    private Long obraSocial;

    private Integer actividadAfip;

    private Integer localidadAfip;

    private Integer situacionAfip;

    private Integer modeloContratacionAfip;

    private String search;

    public String getApellidoNombre() {
        return MessageFormat.format("{0}, {1}", this.apellido, this.nombre);
    }
}
