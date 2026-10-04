package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.infrastructure.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import um.haberes.core.hexagonal.personas.dependencia.infrastructure.persistence.entity.DependenciaEntity;
import um.haberes.core.hexagonal.personas.persona.infrastructure.persistence.entity.PersonaEntity;
import um.haberes.core.model.AuditableEntity;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.persistence.entity.CategoriaEntity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "cargo_liquidacion")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CargoLiquidacionEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cargoLiquidacionId = null;

    private Long legajoId = null;

    private Integer anho = null;

    private Integer mes = null;

    private Integer dependenciaId = null;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaDesde = null;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaHasta = null;

    private Integer categoriaId = null;

    private String categoriaNombre = "";

    private BigDecimal categoriaBasico = BigDecimal.ZERO;

    private BigDecimal estadoDocente = BigDecimal.ZERO;

    private BigDecimal horasJornada = BigDecimal.ZERO;

    private int jornada = 0;

    private int presentismo = 0;

    private String situacion = null;

    @OneToOne
    @JoinColumn(name = "legajoId", insertable = false, updatable = false)
    private PersonaEntity persona = null;

    @OneToOne
    @JoinColumn(name = "dependenciaId", insertable = false, updatable = false)
    private DependenciaEntity dependencia = null;

    @OneToOne
    @JoinColumn(name = "categoriaId", insertable = false, updatable = false)
    private CategoriaEntity categoria = null;
}
