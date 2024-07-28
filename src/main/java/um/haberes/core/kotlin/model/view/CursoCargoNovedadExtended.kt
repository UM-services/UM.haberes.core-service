package um.haberes.core.kotlin.model.view

import jakarta.persistence.*
import org.hibernate.annotations.Immutable
import um.haberes.core.kotlin.model.Auditable
import java.math.BigDecimal

@Entity
@Table(
    name = "vw_curso_cargo_novedad_extended",
    uniqueConstraints = [UniqueConstraint(columnNames = ["cursoId", "anho", "mes", "cargoTipoId", "legajoId"])]
)
@Immutable
data class CursoCargoNovedadExtended(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var cursoCargoNovedadId: Long? = null,

    var cursoId: Long? = null,
    var anho: Int = 0,
    var mes: Int = 0,
    var cargoTipoId: Int? = null,
    var legajoId: Long? = null,
    var horasSemanales: BigDecimal = BigDecimal.ZERO,
    var horasTotales: BigDecimal = BigDecimal.ZERO,
    var desarraigo: Byte = 0,
    var alta: Byte = 0,
    var baja: Byte = 0,
    var cambio: Byte = 0,
    var solicitud: String? = null,
    var autorizado: Byte = 0,
    var rechazado: Byte = 0,
    var respuesta: String? = null,
    var transferido: Byte = 0,
    var aCargo: Byte = 0,
    var apellido: String = "",
    var nombre: String = ""

) : Auditable()
