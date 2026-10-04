package um.haberes.core.hexagonal.personas.persona.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

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
public class PersonaSearch {

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
    private int estado = 1;

    @Builder.Default
    private String liquida = "S";

    @Builder.Default
    private int estadoAfip = 1;

    private Integer dependenciaId;

    private String salida;

    private Long obraSocial;

    private Integer actividadAfip;

    private Integer localidadAfip;

    private Integer situacionAfip;

    private Integer modeloContratacionAfip;

    private String search;
}
