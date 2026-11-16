package um.haberes.core.hexagonal.liquidaciones.general.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.EstadoLegajoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.MotivoResultadoGeneral;

/** Resultado de un legajo dentro de un proceso masivo. */
@Entity
@Table(name = "liquidacion_general_detalle")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LiquidacionGeneralDetalleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detalle_id")
    private Long detalleId = null;

    @Column(name = "proceso_id", length = 36)
    private String procesoId = null;

    @Column(name = "legajo_id")
    private Long legajoId = null;

    @Column(length = 120)
    private String apellido = null;

    @Column(length = 120)
    private String nombre = null;

    @Enumerated(EnumType.STRING)
    @Column(length = 16)
    private EstadoLegajoGeneral resultado = null;

    @Enumerated(EnumType.STRING)
    @Column(length = 48)
    private MotivoResultadoGeneral motivo = null;

    @Column(length = 500)
    private String detalle = null;

    @Column(name = "total_neto", precision = 15, scale = 2)
    private BigDecimal totalNeto = null;

    @Column(name = "registrado_en")
    private OffsetDateTime registradoEn = null;
}
