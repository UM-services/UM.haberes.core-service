package um.haberes.core.hexagonal.personas.persona.domain.model;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.OffsetDateTime;

import um.haberes.core.hexagonal.personas.dependencia.domain.model.Dependencia;

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
public class Persona {

    private Long legajoId;

    @Builder.Default
    private BigDecimal documento = BigDecimal.ZERO;

    @Builder.Default
    private String apellido = "";

    @Builder.Default
    private String nombre = "";

    private OffsetDateTime nacimiento;

    private OffsetDateTime altaDocente;

    @Builder.Default
    private int ajusteDocente = 0;

    private OffsetDateTime altaAdministrativa;

    @Builder.Default
    private int ajusteAdministrativo = 0;

    @Builder.Default
    private String estadoCivil = "";

    private Integer situacionId;

    @Builder.Default
    private Byte reemplazoDesarraigo = 0;

    @Builder.Default
    private Byte mitadDesarraigo = 0;

    @Builder.Default
    private String cuil = "";

    @Builder.Default
    private int posgrado = 0;

    @Builder.Default
    private int estado = 0;

    @Builder.Default
    private String liquida = "";

    @Builder.Default
    private int estadoAfip = 0;

    private Integer dependenciaId;

    private Dependencia dependencia;

    private String salida;

    private Long obraSocial;

    private Integer actividadAfip;

    private Integer localidadAfip;

    @Builder.Default
    private int situacionAfip = 0;

    private Integer modeloContratacionAfip;

    @Builder.Default
    private Byte directivoEtec = 0;

    public String getApellidoNombre() {
        return MessageFormat.format("{0}, {1}", this.apellido, this.nombre);
    }
}
