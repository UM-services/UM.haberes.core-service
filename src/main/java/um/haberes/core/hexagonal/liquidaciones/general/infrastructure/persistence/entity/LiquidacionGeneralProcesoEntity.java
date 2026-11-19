package um.haberes.core.hexagonal.liquidaciones.general.infrastructure.persistence.entity;

import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.EstadoProcesoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.TipoProcesoGeneral;

/** Proceso masivo de liquidación general (cabecera de avance). */
@Entity
@Table(name = "liquidacion_general_proceso")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LiquidacionGeneralProcesoEntity {

    @Id
    @Column(name = "proceso_id", length = 36)
    private String procesoId = null;

    @Enumerated(EnumType.STRING)
    @Column(length = 16)
    private TipoProcesoGeneral tipo = null;

    private int anho = 0;

    private int mes = 0;

    @Enumerated(EnumType.STRING)
    @Column(length = 16)
    private EstadoProcesoGeneral estado = null;

    private int total = 0;

    private int procesados = 0;

    private int exitosos = 0;

    private int omitidos = 0;

    private int fallidos = 0;

    private boolean forzar = false;

    @Column(name = "iniciado_por")
    private Long iniciadoPor = null;

    @Column(name = "iniciado_en")
    private OffsetDateTime iniciadoEn = null;

    @Column(name = "finalizado_en")
    private OffsetDateTime finalizadoEn = null;

    @Column(length = 500)
    private String mensaje = null;
}
