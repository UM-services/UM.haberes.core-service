package um.haberes.core.hexagonal.liquidaciones.individual.infrastructure.web.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Ficha agregada de liquidación individual.
 *
 * <p>Cada bloque es una proyección del contrato REST; los tipos internos de otros slices no se
 * exponen.
 */
public record FichaLiquidacionIndividualResponse(Long legajoId, Integer anho, Integer mes, Persona persona,
                                                 Control control, Liquidacion liquidacion,
                                                 Antiguedad antiguedad, List<Cargo> cargosDocentes,
                                                 List<Cargo> cargosNoDocentes,
                                                 List<CargoConClase> cargosConClase,
                                                 List<Novedad> novedades, List<Item> items,
                                                 Acciones acciones) {

    public record Persona(Long legajoId, BigDecimal documento, String apellido, String nombre,
                          Integer estado, String liquida, Integer dependenciaId, String salida) {
    }

    public record Control(Long controlId, Integer anho, Integer mes, Integer modoLiquidacionId) {
    }

    public record Liquidacion(Long liquidacionId, Byte bloqueado, Integer estado, String liquida,
                              BigDecimal totalRemunerativo, BigDecimal totalNoRemunerativo,
                              BigDecimal totalDeduccion, BigDecimal totalNeto,
                              @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX",
                                      timezone = "UTC") OffsetDateTime fechaLiquidacion,
                              @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX",
                                      timezone = "UTC") OffsetDateTime fechaAcreditacion) {
    }

    public record Antiguedad(Integer mesesDocentes, Integer mesesAdministrativos, BigDecimal indiceDocente,
                             BigDecimal indiceAdministrativo) {
    }

    public record Cargo(Long cargoLiquidacionId, Integer dependenciaId, String dependenciaAcronimo,
                        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX",
                                timezone = "UTC") OffsetDateTime fechaDesde,
                        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX",
                                timezone = "UTC") OffsetDateTime fechaHasta,
                        String categoriaNombre, BigDecimal categoriaBasico, BigDecimal estadoDocente,
                        BigDecimal horasJornada, int jornada, int presentismo, String situacion) {
    }

    public record CargoConClase(Long cargoClaseDetalleId, String descripcion, String facultadReducido,
                                String sedeNombre, Long periodoDesde, Long periodoHasta, int horas,
                                BigDecimal valorHora) {
    }

    public record Novedad(Long novedadId, Integer codigoId, String codigoNombre, BigDecimal importe,
                          String value, String observaciones) {
    }

    public record Item(Long itemId, Integer codigoId, String codigoNombre, BigDecimal importe) {
    }

    public record Acciones(boolean puedeGenerarCargos, boolean puedeLiquidar,
                           boolean puedeEliminarLiquidacion, boolean puedeEditarNovedades,
                           boolean bloqueado, String motivoBloqueo) {
    }
}
