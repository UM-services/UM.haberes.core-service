package um.haberes.core.service.facade.liquidaciones;

import lombok.Data;
import um.haberes.core.kotlin.model.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class LiquidacionState {
    private Control control;
    private Persona persona;
    private List<BigDecimal> indices;
    private Map<Integer, Codigo> codigos;
    private Map<Integer, Item> items = new HashMap<>();
    private Map<Integer, Novedad> novedades;
}
