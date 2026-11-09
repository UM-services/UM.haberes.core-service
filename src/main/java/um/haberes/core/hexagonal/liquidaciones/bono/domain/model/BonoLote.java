package um.haberes.core.hexagonal.liquidaciones.bono.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * Lote de bonos de una dependencia para un período.
 *
 * <p>Los catálogos (dependencias, código 981 y grupos de códigos) viajan una sola vez para todo
 * el lote: son los mismos para todos los legajos y antes se repetían en cada llamada. El
 * {@code control} del período también es único. Es un modelo de lectura: no representa una
 * entidad persistida.
 */
public record BonoLote(Integer anho, Integer mes, Integer dependenciaId, String salida,
                       Control control, Catalogo catalogo, List<Legajo> legajos) {

    public BonoLote {
        legajos = legajos == null ? List.of() : List.copyOf(legajos);
    }

    public record Control(OffsetDateTime fechaDesde, OffsetDateTime fechaHasta,
                          OffsetDateTime fechaPago, OffsetDateTime fechaDeposito,
                          String aporteJubilatorio, BigDecimal horaReferenciaEtec) {
    }

    public record Catalogo(List<Dependencia> dependencias, Codigo codigo981,
                           List<Grupo> remunerativos, List<Grupo> noRemunerativos,
                           List<Grupo> deducciones) {

        public Catalogo {
            dependencias = dependencias == null ? List.of() : List.copyOf(dependencias);
            remunerativos = remunerativos == null ? List.of() : List.copyOf(remunerativos);
            noRemunerativos = noRemunerativos == null ? List.of() : List.copyOf(noRemunerativos);
            deducciones = deducciones == null ? List.of() : List.copyOf(deducciones);
        }
    }

    public record Dependencia(Integer dependenciaId, String acronimo) {
    }

    public record Codigo(Integer codigoId, String nombre) {
    }

    public record Grupo(Integer codigoId, String nombre) {
    }

    public record Legajo(Long legajoId, Persona persona, Antiguedad antiguedad,
                         List<BigDecimal> indices, String cbu, List<Item> items,
                         List<CursoCargo> cursos, List<CursoCargo> cursosEtec,
                         List<CargoLiquidado> docentes, List<CargoLiquidado> noDocentes,
                         List<Clase> clases, List<Adicional> adicionales,
                         BigDecimal porcentajeAntiguedadEtec, String mailInstitucional,
                         Long legajoControlId, boolean bonoEnviado) {

        public Legajo {
            indices = indices == null ? List.of() : List.copyOf(indices);
            items = items == null ? List.of() : List.copyOf(items);
            cursos = cursos == null ? List.of() : List.copyOf(cursos);
            cursosEtec = cursosEtec == null ? List.of() : List.copyOf(cursosEtec);
            docentes = docentes == null ? List.of() : List.copyOf(docentes);
            noDocentes = noDocentes == null ? List.of() : List.copyOf(noDocentes);
            clases = clases == null ? List.of() : List.copyOf(clases);
            adicionales = adicionales == null ? List.of() : List.copyOf(adicionales);
        }
    }

    public record Persona(Long legajoId, String apellido, String nombre, String documento,
                          String cuil, Integer dependenciaId, OffsetDateTime altaAdministrativa,
                          byte directivoEtec) {
    }

    public record Antiguedad(int mesesDocentes, int mesesAdministrativos) {
    }

    public record Item(Integer codigoId, BigDecimal importe) {
    }

    public record CursoCargo(String cargo, String curso, byte desarraigo, byte anual, byte semestre1,
                             byte semestre2, BigDecimal horasSemanales) {
    }

    public record CargoLiquidado(String dependencia, String categoria, BigDecimal basico,
                                 BigDecimal horasJornada, int jornada) {
    }

    public record Clase(String dependencia, String clase, String cargoClase, String descripcion,
                        String geografica, int horas, BigDecimal valorHora) {
    }

    public record Adicional(Integer dependenciaId, BigDecimal adicional) {
    }
}
