package um.haberes.core.service.facade.liquidaciones;

import lombok.*;
import um.haberes.core.hexagonal.etec.control_etec.domain.model.ControlEtec;
import um.haberes.core.hexagonal.etec.liquidacion.domain.model.AcumuladorEtec;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.model.Codigo;
import um.haberes.core.hexagonal.liquidaciones.item.domain.model.Item;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;
import um.haberes.core.model.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiquidacionState {

    private ControlEntity control;
    private Persona persona;
    private List<BigDecimal> indices;
    private Map<Integer, Codigo> codigos;
    @Builder.Default
    private Map<Integer, Item> items = new HashMap<>();
    private Map<Integer, Novedad> novedades;
    private List<CargoClaseDetalleEntity> cargoClases;
    private ControlEtec controlEtec;
    @Builder.Default
    private AcumuladorEtec etecAcumulado = new AcumuladorEtec();
    /**
     * Porcentaje de arraigo ETEC del legajo según su antigüedad docente. Lo resuelve
     * {@code inicializarLiquidacion} con la escala global; cero si no hay antigüedad o no alcanza
     * el primer tramo.
     */
    @Builder.Default
    private BigDecimal porcentajeArraigoEtec = BigDecimal.ZERO;

    /**
     * Copia los datos de contexto del período (control, persona, índices, códigos, novedades y
     * control ETEC) desde el estado recién construido por {@code makeContext}.
     *
     * <p>Es la inicialización del estado de trabajo de una liquidación: el estado destino parte
     * vacío y sin esta copia el motor no vería el {@code control_etec} del período.</p>
     */
    public void copiarContexto(LiquidacionState origen) {
        this.control = origen.control;
        this.persona = origen.persona;
        this.indices = origen.indices;
        this.codigos = origen.codigos;
        this.novedades = origen.novedades;
        this.controlEtec = origen.controlEtec;
    }
}
