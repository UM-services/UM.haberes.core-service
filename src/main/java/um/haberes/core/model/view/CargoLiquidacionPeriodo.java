package um.haberes.core.model.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Immutable;
import um.haberes.core.model.AuditableEntity;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.persistence.entity.CategoriaEntity;
import um.haberes.core.hexagonal.personas.dependencia.infrastructure.persistence.entity.DependenciaEntity;
import um.haberes.core.hexagonal.personas.persona.infrastructure.persistence.entity.PersonaEntity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "vw_cargo_liquidacion_periodo")
@Immutable
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CargoLiquidacionPeriodo extends AuditableEntity {

    @Id
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

    private String categoriaNombre = null;

    private BigDecimal categoriaBasico = null;

    private int jornada = 0;

    private int presentismo = 0;

    private BigDecimal asignacionEspecialPermanente = BigDecimal.ZERO;

    private String situacion = null;

    private Long periodo = null;

    @OneToOne(optional = true)
    @JoinColumn(name = "legajoId", insertable = false, updatable = false)
    private PersonaEntity persona = null;

    @OneToOne(optional = true)
    @JoinColumn(name = "dependenciaId", insertable = false, updatable = false)
    private DependenciaEntity dependencia = null;

    @OneToOne(optional = true)
    @JoinColumn(name = "categoriaId", insertable = false, updatable = false)
    private CategoriaEntity categoria = null;
}
